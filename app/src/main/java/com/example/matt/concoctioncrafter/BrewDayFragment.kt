/* Copyright Matthew Coughlin 2018, 2019 */

package com.example.matt.concoctioncrafter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.matt.concoctioncrafter.MainActivity.Companion.RECIPE_KEY
import com.example.matt.concoctioncrafter.data.Hop
import com.example.matt.concoctioncrafter.data.Recipe
import io.reactivex.disposables.Disposable
import java.util.*

class BrewDayFragment : Fragment() {
    private var startBoilButton: Button? = null
    private var _recipeName: TextView? = null
    private var _hopList: LinearLayout? = null
    private var _alcoholContent: TextView? = null
    private var _recipeSubscription: Disposable? = null
    private var _recipe: Recipe? = null

    private var recipeName: String
        get() = _recipeName!!.text.toString()
        set(name) {
            _recipeName!!.text = name
        }

    private var alcoholContent: Float
        get() = java.lang.Float.valueOf(_alcoholContent!!.text.toString())
        set(alcoholContent) {
            _alcoholContent!!.text = String.format(Locale.getDefault(), "%.2f", alcoholContent)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("TESTING", "Creating BrewDayFragment")
        super.onCreate(savedInstanceState)

        if (activity != null) {
            _recipeSubscription = (activity as MainActivity).recipe.subscribe({ (_recipeName, _style, _fermentables, _hops, _yeast) ->
                _recipe = Recipe(_recipeName, _style, _fermentables, _hops, _yeast)
                restoreSavedState(savedInstanceState)
            }, { throwable -> Log.e("Brew_Day_Fragment", "Failed to get the recipe", throwable) })
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.brew_day_fragment, container, false) as ViewGroup

        startBoilButton = rootView.findViewById(R.id.start_boil_button)
        startBoilButton?.setOnClickListener { Toast.makeText(context, "This feature is coming soon!", Toast.LENGTH_SHORT).show() }
        _recipeName = rootView.findViewById(R.id.name)
        _hopList = rootView.findViewById(R.id.hop_info_list)
        _alcoholContent = rootView.findViewById(R.id.actual_ac)

        restoreSavedState(savedInstanceState)

        return rootView
    }

    override fun onDestroy() {
        _recipeSubscription!!.dispose()
        super.onDestroy()
    }

    /**
     * Save the recipe.
     *
     * @param outState The saved instance state bundle.
     * @see android.view.View.onSaveInstanceState
     */
    override fun onSaveInstanceState(outState: Bundle) {
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

        _recipe?._hops = hops
        outState.putParcelable(RECIPE_KEY, _recipe)
        super.onSaveInstanceState(outState)
    }

    /**
     * Restore the recipe information.
     */
    private fun restoreSavedState(savedInstanceState: Bundle?) {
        if (_recipe != null) {
            recipeName = _recipe!!._recipeName
            setHopViews(_recipe?._hops as ArrayList<Hop>)
        } else if (savedInstanceState != null) {
            _recipe = savedInstanceState.getParcelable(RECIPE_KEY)

            if (_recipe != null) {
                recipeName = _recipe!!._recipeName
                setHopViews(_recipe?._hops)
            }
        }
    }

    private fun setHopViews(hops: List<Hop>?) {
        _hopList!!.removeAllViews()

        if (hops != null) {
            for (hop in hops) {
                val newRow = layoutInflater.inflate(R.layout.hop_info_row, activity!!.findViewById(R.id.hop_info_list), false)
                newRow.findViewById<TextView>(R.id.hop_name).text = hop.name
                newRow.findViewById<TextView>(R.id.hop_amount).text = "%.2f".format(hop.amount_oz)
                newRow.findViewById<TextView>(R.id.hop_time).text = if (hop.additionTime_min != -1) hop.additionTime_min.toString() else ""

                if (_hopList!!.parent != null) (_hopList!!.parent as ViewGroup).removeView(newRow)
                _hopList!!.addView(newRow)
            }
        }
    }

    private fun setStartBoilButton(text: CharSequence) {
        startBoilButton?.text = text
    }

    private fun importRecipe(recipe: Recipe) {
        recipeName = recipe._recipeName
        setHopViews(recipe._hops)
    }

    fun clear() {
        recipeName = getString(R.string.recipe_name)
        _hopList?.removeAllViews()
        _alcoholContent?.text = ""
    }
}
