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
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.example.matt.concoctioncrafter.data.Fermentable
import com.example.matt.concoctioncrafter.data.Hop
import io.reactivex.disposables.Disposable


class RecipeFragment : Fragment() {
    private val FERMENTABLE_KEY = "FERMENTABLE_KEY"
    private val HOP_KEY = "HOP_KEY"
    private var _beerName: EditText? = null
    private var _grainList: LinearLayout? = null
    private var _hopList: LinearLayout? = null
    private var _addFermentableButton: Button? = null
    private var _addHopButton: Button? = null
    private var _yeast: Spinner? = null
    private var _style: Spinner? = null
    private var _recipeSubscription: Disposable? = null

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

    /**
     * Save the grains and hops.
     *
     * @param outState The saved instance state bundle.
     * @see android.view.View.onSaveInstanceState
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(FERMENTABLE_KEY, getFermentablesFromList() as ArrayList<Fermentable>)
        outState.putParcelableArrayList(HOP_KEY, getHopsFromList() as ArrayList<Hop>)
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (activity != null) {
            _recipeSubscription = (activity as MainActivity).recipe.subscribe({ (_recipeName,
                                                                                        _style,
                                                                                        _fermentables,
                                                                                        _hops,
                                                                                        _yeast) ->
                beerName = _recipeName

                _grainList!!.removeAllViews()
                _hopList!!.removeAllViews()
                for (fermentable in _fermentables) {
                    val newRow = layoutInflater.inflate(R.layout.grain_row, activity!!.findViewById(R.id.grain_list))
                    setSpinner(newRow, R.id.spinner, fermentable.name)
                    newRow.findViewById<EditText>(R.id.amount).setText("%.2f".format(fermentable.amount_lbs))
                    _grainList!!.removeView(newRow)
                    _grainList!!.addView(newRow)
                }

                for (hop in _hops) {
                    val newRow = layoutInflater.inflate(R.layout.hop_row, activity!!.findViewById(R.id.hop_list))
                    setSpinner(newRow, R.id.spinner, hop.name)
                    newRow.findViewById<EditText>(R.id.amount).setText("%.2f".format(hop.amount_oz))
                    newRow.findViewById<EditText>(R.id.time).setText(hop.additionTime_min.toString())
                    _hopList!!.removeView(newRow)
                    _hopList!!.addView(newRow)
                }

                yeast = _yeast
                style = _style
            }, { throwable -> Log.e("Recipe_Fragment", "Failed to get the recipe", throwable) })
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.recipe_fragment, container, false) as ViewGroup

        _beerName = rootView.findViewById(R.id.name_input)
        _grainList = rootView.findViewById(R.id.grain_list)
        _hopList = rootView.findViewById(R.id.hop_list)
        _yeast = rootView.findViewById(R.id.yeast_spinner)
        _style = rootView.findViewById(R.id.style_spinner)
        _addFermentableButton = rootView.findViewById(R.id.add_grain_button)
        _addHopButton = rootView.findViewById(R.id.add_hop_button)

        _addFermentableButton!!.setOnClickListener {
            layoutInflater.inflate(R.layout.grain_row, _grainList, true)
        }

        _addHopButton!!.setOnClickListener {
            layoutInflater.inflate(R.layout.hop_row, _hopList, true)
        }

        _grainList!!.removeAllViews()
        _hopList!!.removeAllViews()
        restoreSavedState(savedInstanceState)

        return rootView
    }

    override fun onDestroy() {
        _recipeSubscription!!.dispose()
        super.onDestroy()
    }

    private fun setSpinner(parent: View, @IdRes viewId: Int, text: String) {
        val spinner = parent.findViewById<Spinner>(viewId)
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i) == text) {
                spinner.setSelection(i)
                break
            }
        }
    }

    private fun restoreSavedState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val fermentables = savedInstanceState.getParcelableArrayList<Fermentable>(FERMENTABLE_KEY)
            val hops = savedInstanceState.getParcelableArrayList<Hop>(HOP_KEY)

            if (fermentables != null) {
                for (fermentable in fermentables) {
                    val newRow = layoutInflater.inflate(R.layout.grain_row, activity!!.findViewById(R.id.grain_list))
                    setSpinner(newRow, R.id.spinner, fermentable.name)
                    newRow.findViewById<EditText>(R.id.amount).setText("%.2f".format(fermentable.amount_lbs))
                    _grainList!!.addView(newRow)
                }
            }

            if (hops != null) {
                for (hop in hops) {
                    val newRow = layoutInflater.inflate(R.layout.hop_row, activity!!.findViewById(R.id.hop_list))
                    setSpinner(newRow, R.id.spinner, hop.name)
                    newRow.findViewById<EditText>(R.id.amount).setText("%.2f".format(hop.amount_oz))
                    newRow.findViewById<EditText>(R.id.time).setText(if (hop.additionTime_min != -1) hop.additionTime_min.toString() else "")
                    _hopList!!.addView(newRow)
                }
            }
        }
    }

    private fun getFermentablesFromList(): List<Fermentable> {
        val fermentables = ArrayList<Fermentable>()
        val list = activity!!.findViewById<LinearLayout>(R.id.grain_list)

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
}
