/* Copyright Matthew Coughlin 2018, 2019 */

package com.example.matt.concoctioncrafter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.Disposable

class RecipeFragment : Fragment() {
    private var _beerName: EditText? = null
    private var _grainList: RecyclerView? = null
    private var _hopList: RecyclerView? = null
    private var _yeast: Spinner? = null
    private var _style: Spinner? = null
    private var _grainAdapter: GrainAdapter? = null
    private var _hopAdapter: HopAdapter? = null
    private var _recipeSubscription: Disposable? = null

    var beerName: String
        get() = _beerName!!.text.toString()
        set(beerName) = _beerName!!.setText(beerName)

    var yeast: String
        get() = _yeast!!.selectedItem.toString()
        set(yeast) {
            for (i in 0 until _yeast!!.count) {
                if (_yeast!!.getItemAtPosition(i) == yeast) {
                    _yeast!!.setSelection(i)
                    break
                }
            }
        }

    var style: String
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
        super.onCreate(savedInstanceState)

        if (activity != null) {
            _recipeSubscription = (activity as MainActivity).recipe.subscribe({ (_recipeName, _style, _fermentables, _hops, _yeast) ->
                beerName = _recipeName

                _grainAdapter = GrainAdapter(_fermentables, context)
                _grainList!!.adapter = _grainAdapter

                _hopAdapter = HopAdapter(_hops, context)
                _hopList!!.adapter = _hopAdapter

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

        return rootView
    }

    override fun onDestroy() {
        _recipeSubscription!!.dispose()
        super.onDestroy()
    }

    private fun setSpinner(@IdRes viewId: Int, text: String) {
        val spinner = activity!!.findViewById<Spinner>(viewId)
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i) == text) {
                spinner.setSelection(i)
                break
            }
        }
    }
}
