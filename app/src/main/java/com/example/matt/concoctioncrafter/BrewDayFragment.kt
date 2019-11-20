/* Copyright Matthew Coughlin 2018, 2019 */

package com.example.matt.concoctioncrafter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.matt.concoctioncrafter.data.Recipe
import io.reactivex.disposables.Disposable
import java.util.*

class BrewDayFragment : Fragment() {
    private var startBoilButton: Button? = null
    private var _recipeName: TextView? = null
    private var _hopType1: TextView? = null
    private var _hopType2: TextView? = null
    private var _hopType3: TextView? = null
    private var _hopType4: TextView? = null
    private var _amount1: TextView? = null
    private var _amount2: TextView? = null
    private var _amount3: TextView? = null
    private var _amount4: TextView? = null
    private var _additionTime1: TextView? = null
    private var _additionTime2: TextView? = null
    private var _additionTime3: TextView? = null
    private var _additionTime4: TextView? = null
    private var _alcoholContent: TextView? = null
    private var _recipeSubscription: Disposable? = null

    var recipeName: String
        get() = _recipeName!!.text.toString()
        set(name) {
            _recipeName!!.text = name
        }

    var hopType1: String
        get() = _hopType1!!.text.toString()
        set(type) {
            _hopType1!!.text = type
        }

    var hopType2: String
        get() = _hopType2!!.text.toString()
        set(hop_type2) {
            _hopType2!!.text = hop_type2
        }

    var hopType3: String
        get() = _hopType3!!.text.toString()
        set(hop_type3) {
            _hopType3!!.text = hop_type3
        }

    var hopType4: String
        get() = _hopType4!!.text.toString()
        set(hop_type4) {
            _hopType4!!.text = hop_type4
        }

    var amount1: String
        get() = _amount1!!.text.toString()
        set(amount1) {
            _amount1!!.text = amount1
        }

    var amount2: String
        get() = _amount2!!.text.toString()
        set(amount2) {
            _amount2!!.text = amount2
        }

    var amount3: String
        get() = _amount3!!.text.toString()
        set(amount3) {
            _amount3!!.text = amount3
        }

    var amount4: String
        get() = _amount4!!.text.toString()
        set(amount4) {
            _amount4!!.text = amount4
        }

    var additionTime1: String
        get() = _additionTime1!!.text.toString()
        set(additionTime1) {
            _additionTime1!!.text = additionTime1
        }

    var additionTime2: String
        get() = _additionTime2!!.text.toString()
        set(additionTime2) {
            _additionTime2!!.text = additionTime2
        }

    var additionTime3: String
        get() = _additionTime3!!.text.toString()
        set(additionTime3) {
            _additionTime3!!.text = additionTime3
        }

    var additionTime4: String
        get() = _additionTime4!!.text.toString()
        set(additionTime4) {
            _additionTime4!!.text = additionTime4
        }

    var alcoholContent: Float
        get() = java.lang.Float.valueOf(_alcoholContent!!.text.toString())
        set(alcoholContent) {
            _alcoholContent!!.text = String.format(Locale.getDefault(), "%.2f", alcoholContent)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (activity != null) {
            _recipeSubscription = (activity as MainActivity).recipe.subscribe({ this.importRecipe(it) },
                    { Log.e("Brew_Day_Fragment", "Failed to get the recipe", it) })
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.brew_day_fragment, container, false) as ViewGroup

        startBoilButton = rootView.findViewById(R.id.start_boil_button)
        startBoilButton!!.setOnClickListener { Toast.makeText(context, "This feature is coming soon!", Toast.LENGTH_SHORT).show() }
        _recipeName = rootView.findViewById(R.id.name)
        _hopType1 = rootView.findViewById(R.id.hop_type1)
        _hopType2 = rootView.findViewById(R.id.hop_type2)
        _hopType3 = rootView.findViewById(R.id.hop_type3)
        _hopType4 = rootView.findViewById(R.id.hop_type4)
        _amount1 = rootView.findViewById(R.id.amount1)
        _amount2 = rootView.findViewById(R.id.amount2)
        _amount3 = rootView.findViewById(R.id.amount3)
        _amount4 = rootView.findViewById(R.id.amount4)
        _additionTime1 = rootView.findViewById(R.id.time1)
        _additionTime2 = rootView.findViewById(R.id.time2)
        _additionTime3 = rootView.findViewById(R.id.time3)
        _additionTime4 = rootView.findViewById(R.id.time4)
        _alcoholContent = rootView.findViewById(R.id.actual_ac)

        if (arguments != null) {
            val recipe = arguments!!.getParcelable<Recipe>(MainActivity.RECIPE_KEY)

            if (recipe != null) {
                importRecipe(recipe)
            }
        }

        return rootView
    }

    override fun onDestroy() {
        _recipeSubscription!!.dispose()
        super.onDestroy()
    }

    fun setStartBoilButton(text: CharSequence) {
        startBoilButton!!.text = text
    }

    private fun importRecipe(recipe: Recipe) {
        recipeName = recipe._recipeName

        for (hop in recipe._hops) {
            // TODO: Update the hop list. This requires changing the list to a linearlayout
        }
    }

    fun clear() {
        _hopType1?.text = getString(R.string.none)
        _hopType2?.text = getString(R.string.none)
        _hopType3?.text = getString(R.string.none)
        _hopType4?.text = getString(R.string.none)

        _amount1?.text = getString(R.string.none)
        _amount2?.text = getString(R.string.none)
        _amount3?.text = getString(R.string.none)
        _amount4?.text = getString(R.string.none)

        _additionTime1?.text = getString(R.string.none)
        _additionTime2?.text = getString(R.string.none)
        _additionTime3?.text = getString(R.string.none)
        _additionTime4?.text = getString(R.string.none)
    }
}
