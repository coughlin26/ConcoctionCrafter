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
import io.reactivex.disposables.Disposable
import java.util.*

class RecipeFragment : Fragment() {
    private var _beerName: EditText? = null
    private var _grainInput1: EditText? = null
    private var _grainInput2: EditText? = null
    private var _grainInput3: EditText? = null
    private var _grainInput4: EditText? = null
    private var _hopInput1: EditText? = null
    private var _hopInput2: EditText? = null
    private var _hopInput3: EditText? = null
    private var _hopInput4: EditText? = null
    private var _grainSpinner1: Spinner? = null
    private var _grainSpinner2: Spinner? = null
    private var _grainSpinner3: Spinner? = null
    private var _grainSpinner4: Spinner? = null
    private var _hopSpinner1: Spinner? = null
    private var _hopSpinner2: Spinner? = null
    private var _hopSpinner3: Spinner? = null
    private var _hopSpinner4: Spinner? = null
    private var _hopAdditionTime_1: EditText? = null
    private var _hopAdditionTime_2: EditText? = null
    private var _hopAdditionTime_3: EditText? = null
    private var _hopAdditionTime_4: EditText? = null
    private var _yeast: Spinner? = null
    private var _recipeSubscription: Disposable? = null

    var beerName: String
        get() = _beerName!!.text.toString()
        set(beerName) = _beerName!!.setText(beerName)

    var grainInput1: String
        get() = _grainInput1!!.text.toString()
        set(grainInput1) = _grainInput1!!.setText(grainInput1)

    var grainInput2: String
        get() = _grainInput2!!.text.toString()
        set(grainInput1) = _grainInput2!!.setText(grainInput1)

    var grainInput3: String
        get() = _grainInput3!!.text.toString()
        set(grainInput1) = _grainInput3!!.setText(grainInput1)

    var grainInput4: String
        get() = _grainInput4!!.text.toString()
        set(grainInput1) = _grainInput4!!.setText(grainInput1)

    var grainSpinner1: String
        get() = _grainSpinner1!!.selectedItem.toString()
        set(grain) {
            for (i in 0 until _grainSpinner1!!.count) {
                if (_grainSpinner1!!.getItemAtPosition(i) == grain) {
                    _grainSpinner1!!.setSelection(i)
                    break
                }
            }
        }

    var grainSpinner2: String
        get() = _grainSpinner2!!.selectedItem.toString()
        set(grain) {
            for (i in 0 until _grainSpinner2!!.count) {
                if (_grainSpinner2!!.getItemAtPosition(i) == grain) {
                    _grainSpinner2!!.setSelection(i)
                    break
                }
            }
        }

    var grainSpinner3: String
        get() = _grainSpinner3!!.selectedItem.toString()
        set(grain) {
            for (i in 0 until _grainSpinner3!!.count) {
                if (_grainSpinner3!!.getItemAtPosition(i) == grain) {
                    _grainSpinner3!!.setSelection(i)
                    break
                }
            }
        }

    var grainSpinner4: String
        get() = _grainSpinner4!!.selectedItem.toString()
        set(grain) {
            for (i in 0 until _grainSpinner4!!.count) {
                if (_grainSpinner4!!.getItemAtPosition(i) == grain) {
                    _grainSpinner4!!.setSelection(i)
                    break
                }
            }
        }

    var hopInput1: String
        get() = _hopInput1!!.text.toString()
        set(hopInput1) = _hopInput1!!.setText(hopInput1)

    var hopInput2: String
        get() = _hopInput2!!.text.toString()
        set(hopInput2) = _hopInput2!!.setText(hopInput2)

    var hopInput3: String
        get() = _hopInput3!!.text.toString()
        set(hopInput3) = _hopInput3!!.setText(hopInput3)

    var hopInput4: String
        get() = _hopInput4!!.text.toString()
        set(hopInput4) = _hopInput4!!.setText(hopInput4)

    var hopSpinner1: String
        get() = _hopSpinner1!!.selectedItem.toString()
        set(hopSpinner1) {
            for (i in 0 until _hopSpinner1!!.count) {
                if (_hopSpinner1!!.getItemAtPosition(i) == hopSpinner1) {
                    _hopSpinner1!!.setSelection(i)
                    break
                }
            }
        }

    var hopSpinner2: String
        get() = _hopSpinner2!!.selectedItem.toString()
        set(hopSpinner2) {
            for (i in 0 until _hopSpinner2!!.count) {
                if (_hopSpinner2!!.getItemAtPosition(i) == hopSpinner2) {
                    _hopSpinner2!!.setSelection(i)
                    break
                }
            }
        }

    var hopSpinner3: String
        get() = _hopSpinner3!!.selectedItem.toString()
        set(hopSpinner3) {
            for (i in 0 until _hopSpinner3!!.count) {
                if (_hopSpinner3!!.getItemAtPosition(i) == hopSpinner3) {
                    _hopSpinner3!!.setSelection(i)
                    break
                }
            }
        }

    var hopSpinner4: String
        get() = _hopSpinner4!!.selectedItem.toString()
        set(hopSpinner4) {
            for (i in 0 until _hopSpinner4!!.count) {
                if (_hopSpinner4!!.getItemAtPosition(i) == hopSpinner4) {
                    _hopSpinner4!!.setSelection(i)
                    break
                }
            }
        }

    val hopAdditionTime_1: String
        get() = _hopAdditionTime_1!!.text.toString()

    val hopAdditionTime_2: String
        get() = _hopAdditionTime_2!!.text.toString()

    val hopAdditionTime_3: String
        get() = _hopAdditionTime_3!!.text.toString()

    val hopAdditionTime_4: String
        get() = _hopAdditionTime_4!!.text.toString()

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (activity != null) {
            _recipeSubscription = (activity as MainActivity).recipe.subscribe({ (_recipeName, _, _fermentables, _hops, _yeast1) ->
                beerName = _recipeName

                grainSpinner1 = _fermentables[0].name
                grainInput1 = String.format(Locale.getDefault(), "%.2f", _fermentables[0].amount_lbs)
                grainSpinner2 = _fermentables[1].name
                grainInput2 = String.format(Locale.getDefault(), "%.2f", _fermentables[1].amount_lbs)
                grainSpinner3 = _fermentables[2].name
                grainInput3 = String.format(Locale.getDefault(), "%.2f", _fermentables[2].amount_lbs)
                grainSpinner4 = _fermentables[3].name
                grainInput4 = String.format(Locale.getDefault(), "%.2f", _fermentables[3].amount_lbs)

                hopSpinner1 = _hops[0].name
                hopInput1 = String.format(Locale.getDefault(), "%.2f", _hops[0].amount_oz)
                setHopAdditionTime_1(Integer.toString(_hops[0].additionTime_min))
                hopSpinner2 = _hops[1].name
                hopInput2 = String.format(Locale.getDefault(), "%.2f", _hops[1].amount_oz)
                setHopAdditionTime_2(Integer.toString(_hops[1].additionTime_min))
                hopSpinner3 = _hops[2].name
                hopInput3 = String.format(Locale.getDefault(), "%.2f", _hops[2].amount_oz)
                setHopAdditionTime_3(Integer.toString(_hops[2].additionTime_min))
                hopSpinner4 = _hops[3].name
                hopInput4 = String.format(Locale.getDefault(), "%.2f", _hops[3].amount_oz)
                setHopAdditionTime_4(Integer.toString(_hops[3].additionTime_min))

                yeast = _yeast1
            }, { throwable -> Log.e("Recipe_Fragment", "Failed to get the recipe", throwable) })
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.recipe_fragment, container, false) as ViewGroup

        _beerName = rootView.findViewById(R.id.name_input)
        _grainInput1 = rootView.findViewById(R.id.amount_input1)
        _grainInput2 = rootView.findViewById(R.id.amount_input2)
        _grainInput3 = rootView.findViewById(R.id.amount_input3)
        _grainInput4 = rootView.findViewById(R.id.amount_input4)
        _hopInput1 = rootView.findViewById(R.id.hop_amount_input1)
        _hopInput2 = rootView.findViewById(R.id.hop_amount_input2)
        _hopInput3 = rootView.findViewById(R.id.hop_amount_input3)
        _hopInput4 = rootView.findViewById(R.id.hop_amount_input4)
        _grainSpinner1 = rootView.findViewById(R.id.grain_spinner1)
        _grainSpinner2 = rootView.findViewById(R.id.grain_spinner2)
        _grainSpinner3 = rootView.findViewById(R.id.grain_spinner3)
        _grainSpinner4 = rootView.findViewById(R.id.grain_spinner4)
        _hopSpinner1 = rootView.findViewById(R.id.hop_spinner1)
        _hopSpinner2 = rootView.findViewById(R.id.hop_spinner2)
        _hopSpinner3 = rootView.findViewById(R.id.hop_spinner3)
        _hopSpinner4 = rootView.findViewById(R.id.hop_spinner4)
        _hopAdditionTime_1 = rootView.findViewById(R.id.addition_time1)
        _hopAdditionTime_2 = rootView.findViewById(R.id.addition_time2)
        _hopAdditionTime_3 = rootView.findViewById(R.id.addition_time3)
        _hopAdditionTime_4 = rootView.findViewById(R.id.addition_time4)
        _yeast = rootView.findViewById(R.id.yeast_spinner)

        return rootView
    }

    override fun onDestroy() {
        _recipeSubscription!!.dispose()
        super.onDestroy()
    }

    fun setHopAdditionTime_1(text: CharSequence) {
        _hopAdditionTime_1!!.setText(text)
    }

    fun setHopAdditionTime_2(text: CharSequence) {
        _hopAdditionTime_2!!.setText(text)
    }

    fun setHopAdditionTime_3(text: CharSequence) {
        _hopAdditionTime_3!!.setText(text)
    }

    fun setHopAdditionTime_4(text: CharSequence) {
        _hopAdditionTime_4!!.setText(text)
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
