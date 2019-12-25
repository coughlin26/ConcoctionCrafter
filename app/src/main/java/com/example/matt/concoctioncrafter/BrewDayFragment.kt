/* Copyright Matthew Coughlin 2018, 2019 */

package com.example.matt.concoctioncrafter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
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

    private var recipeName: String
        get() = _recipeName!!.text.toString()
        set(name) {
            if (_recipeName != null)
                _recipeName!!.text = name
            else
                Log.w("Brew_Day_Fragment", "Recipe Name view is null")
        }

    private var alcoholContent: Float
        get() = java.lang.Float.valueOf(_alcoholContent!!.text.toString())
        set(alcoholContent) {
            _alcoholContent!!.text = String.format(Locale.getDefault(), "%.2f", alcoholContent)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (activity != null) {
            _recipeSubscription = (activity as MainActivity).recipeSubject.subscribe({ recipe ->
                restoreRecipeViews(recipe)
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

        return rootView
    }

    override fun onDestroy() {
        _recipeSubscription?.dispose()
        super.onDestroy()
    }

    /**
     * Restore the recipe information.
     */
    private fun restoreRecipeViews(recipe: Recipe?) {
        if (recipe != null) {
            recipeName = recipe.recipeName
            setHopViews(recipe.hops as ArrayList<Hop>)
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
}
