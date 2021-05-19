/* Copyright Matthew Coughlin 2018, 2019, 2020 */

package dev.mattcoughlin.concoctioncrafter

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
import dev.mattcoughlin.concoctioncrafter.data.Fermentable
import dev.mattcoughlin.concoctioncrafter.data.Hop
import dev.mattcoughlin.concoctioncrafter.data.Recipe
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

    private var beerName: String
        get() = _beerName!!.text.toString()
        set(beerName) {
            if (_beerName != null)
                _beerName!!.setText(beerName)
            else
                Log.w("Recipe_Fragment", "Beer Name view is null")
        }

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
            _recipeSubscription = (activity as MainActivity).recipeSubject.subscribe({ recipe ->
                restoreRecipeViews(recipe)
            }, { throwable -> Log.e("Recipe_Fragment", "Failed to get the recipe", throwable) })
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
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

        return rootView
    }

    override fun onDestroy() {
        _recipeSubscription?.dispose()
        super.onDestroy()
    }

    private fun restoreRecipeViews(recipe: Recipe?) {
        if (recipe != null) {
            beerName = recipe.recipeName
            yeast = recipe.yeast
            style = recipe.style
            setFermentableViews(recipe.fermentables)
            setHopViews(recipe.hops)
        }

        Log.d("TESTING", "Fermentables: ${recipe?.fermentables}")
        Log.d("TESTING", "Hops: ${recipe?.hops}")
    }

    private fun setFermentableViews(fermentables: List<Fermentable>?) {
        _fermentableList!!.removeAllViews()

        if (fermentables != null) {
            for (fermentable in fermentables) {
                Log.d("TESTING", "Adding row for ${fermentable.name}")
                val newRow = layoutInflater.inflate(R.layout.fermentable_row, requireActivity().findViewById(R.id.fermentable_list), false)
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
            for (hop in hops.sortedByDescending { hop -> hop.additionTime_min }) {
                val newRow = layoutInflater.inflate(
                        R.layout.hop_row,
                        requireActivity().findViewById(R.id.hop_list),
                        false)
                setSpinner(newRow, hop.name)
                newRow.findViewById<EditText>(R.id.amount).setText("%.2f".format(hop.amount_oz))
                newRow.findViewById<EditText>(R.id.time).setText(
                        if (hop.additionTime_min != -1) hop.additionTime_min.toString() else "")

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
}
