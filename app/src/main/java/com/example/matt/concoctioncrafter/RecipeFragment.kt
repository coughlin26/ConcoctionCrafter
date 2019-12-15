/* Copyright Matthew Coughlin 2018, 2019 */

package com.example.matt.concoctioncrafter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.matt.concoctioncrafter.MainActivity.Companion.RECIPE_KEY
import com.example.matt.concoctioncrafter.data.Fermentable
import com.example.matt.concoctioncrafter.data.Hop
import com.example.matt.concoctioncrafter.data.Recipe
import io.reactivex.disposables.Disposable


class RecipeFragment : Fragment() {
    private var _recipeLayout: ConstraintLayout? = null
    private var _beerName: EditText? = null
    private var _fermentableList: LinearLayout? = null
    private var _hopList: LinearLayout? = null
    private var _addFermentableButton: Button? = null
    private var _addHopButton: Button? = null
    private var _yeast: Spinner? = null
    private var _style: Spinner? = null
    private var _recipeSubscription: Disposable? = null
    private var _recipe: Recipe? = null

    private var beerName: String
        get() = _beerName!!.text.toString()
        set(beerName) = _beerName!!.setText(beerName)

    private var yeast: String
        get() = _yeast!!.selectedItem.toString()
        set(yeast) {
            for (i in 0 until _yeast!!.count) {
                if (_yeast!!.getItemAtPosition(i) == yeast) {
                    _yeast!!.setSelection(i)
                    break
                }
            }
        }

    private var style: String
        get() = _style!!.selectedItem.toString()
        set(style) {
            for (i in 0 until _style!!.count) {
                if (_style!!.getItemAtPosition(i) == style) {
                    _style!!.setSelection(i)
                    break
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("TESTING", "Creating RecipeFragment")
        super.onCreate(savedInstanceState)

        if (activity != null) {
            _recipeSubscription = (activity as MainActivity).recipe.subscribe({ (_recipeName, _style, _fermentables, _hops, _yeast) ->
                _recipe = Recipe(_recipeName, _style, _fermentables, _hops, _yeast)
                restoreSavedState(savedInstanceState)
            }, { throwable -> Log.e("Recipe_Fragment", "Failed to get the recipe", throwable) })
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("TESTING", "Creating views in RecipeFragment")

        val rootView = inflater.inflate(R.layout.recipe_fragment, container, false) as ViewGroup

        _recipeLayout = rootView.findViewById(R.id.recipe_layout)
        _beerName = rootView.findViewById(R.id.name_input)
        _fermentableList = rootView.findViewById(R.id.fermentable_list)
        _hopList = rootView.findViewById(R.id.hop_list)
        _yeast = rootView.findViewById(R.id.yeast_spinner)
        _style = rootView.findViewById(R.id.style_spinner)
        _addFermentableButton = rootView.findViewById(R.id.add_fermentable_button)
        _addHopButton = rootView.findViewById(R.id.add_hop_button)

        _addFermentableButton?.setOnClickListener {
            layoutInflater.inflate(R.layout.fermentable_row, _fermentableList, true)
        }

        _addHopButton?.setOnClickListener {
            layoutInflater.inflate(R.layout.hop_row, _hopList, true)
        }

        _fermentableList?.removeAllViews()
        _hopList?.removeAllViews()
        restoreSavedState(savedInstanceState)

        return rootView
    }

    override fun onDestroy() {
        _recipeSubscription!!.dispose()
        super.onDestroy()
    }

    /**
     * Save the fermentables and hops.
     *
     * @param outState The saved instance state bundle.
     * @see android.view.View.onSaveInstanceState
     */
    override fun onSaveInstanceState(outState: Bundle) {
        _recipe?._fermentables = getFermentablesFromList()
        _recipe?._hops = getHopsFromList()
        outState.putParcelable(RECIPE_KEY, _recipe)
        super.onSaveInstanceState(outState)
    }

    private fun restoreSavedState(savedInstanceState: Bundle?) {
        if (_recipe != null) {
            beerName = _recipe!!._recipeName
            yeast = _recipe!!._yeast
            style = _recipe!!._style
        } else if (savedInstanceState != null) {
            _recipe = savedInstanceState.getParcelable(RECIPE_KEY)
        }

        Log.d("TESTING", "Fermentables: ${_recipe?._fermentables}")
        Log.d("TESTING", "Hops: ${_recipe?._hops}")

        if (_recipe != null) {
            setFermentableViews(_recipe?._fermentables)
            setHopViews(_recipe?._hops)
        }
    }

    private fun getFermentablesFromList(): List<Fermentable> {
        val fermentables = ArrayList<Fermentable>()
        val list = activity!!.findViewById<LinearLayout>(R.id.fermentable_list)

        for (i in 0 until list.childCount) {
            val row = list.getChildAt(i)
            val amount = row.findViewById<EditText>(R.id.amount).text.toString()
            fermentables.add(Fermentable(row.findViewById<Spinner>(R.id.spinner).selectedItem.toString(),
                    if (amount.isEmpty()) 0f else amount.toFloat()))
        }

        return fermentables
    }

    private fun getHopsFromList(): List<Hop> {
        val hops = ArrayList<Hop>()
        val list = activity!!.findViewById<LinearLayout>(R.id.hop_list)

        for (i in 0 until list.childCount) {
            val row = list.getChildAt(i)
            val amount = row.findViewById<EditText>(R.id.amount).text.toString()
            val time = row.findViewById<EditText>(R.id.time).text.toString()
            hops.add(Hop(row.findViewById<Spinner>(R.id.spinner).selectedItem.toString(),
                    if (amount.isEmpty()) 0f else amount.toFloat(),
                    if (time.isEmpty()) -1 else time.toInt()))
        }

        return hops
    }

    private fun setFermentableViews(fermentables: List<Fermentable>?) {
        _fermentableList!!.removeAllViews()

        if (fermentables != null) {
            for (fermentable in fermentables) {
                Log.d("TESTING", "Adding row for ${fermentable.name}")
                val newRow = layoutInflater.inflate(R.layout.fermentable_row, activity!!.findViewById(R.id.fermentable_list), false)
                setSpinner(newRow, fermentable.name)
                newRow.findViewById<EditText>(R.id.amount).setText("%.2f".format(fermentable.amount_lbs))

                if (_fermentableList!!.parent != null) (_fermentableList!!.parent as ViewGroup).removeView(newRow)
                _fermentableList!!.addView(newRow)
            }
        }
    }

    private fun setHopViews(hops: List<Hop>?) {
        _hopList!!.removeAllViews()

        if (hops != null) {
            for (hop in hops) {
                val newRow = layoutInflater.inflate(R.layout.hop_row, activity!!.findViewById(R.id.hop_list), false)
                setSpinner(newRow, hop.name)
                newRow.findViewById<EditText>(R.id.amount).setText("%.2f".format(hop.amount_oz))
                newRow.findViewById<EditText>(R.id.time).setText(if (hop.additionTime_min != -1) hop.additionTime_min.toString() else "")

                if (_hopList!!.parent != null) (_hopList!!.parent as ViewGroup).removeView(newRow)
                _hopList!!.addView(newRow)
            }
        }
    }

    private fun setSpinner(parent: View, text: String) {
        val spinner = parent.findViewById<Spinner>(R.id.spinner)
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i) == text) {
                Log.d("TESTING", "Setting the spinner value to $text from position $i")
                spinner.setSelection(i)
                break
            }
        }
    }

    fun clear() {
        Log.d("TESTING", "Clearing the recipe")
        _beerName?.text?.clear()
        _fermentableList?.removeAllViews()
        _hopList?.removeAllViews()
        _yeast?.setSelection(0)
        _style?.setSelection(0)
        _recipe = null
    }
}
