/* Copyright Matthew Coughlin 2018, 2019 */

package com.example.matt.concoctioncrafter;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import io.reactivex.disposables.Disposable;

public class RecipeFragment extends Fragment {
    private EditText _beerName;
    private EditText _grainInput1;
    private EditText _grainInput2;
    private EditText _grainInput3;
    private EditText _grainInput4;
    private EditText _hopInput1;
    private EditText _hopInput2;
    private EditText _hopInput3;
    private EditText _hopInput4;
    private Spinner _grainSpinner1;
    private Spinner _grainSpinner2;
    private Spinner _grainSpinner3;
    private Spinner _grainSpinner4;
    private Spinner _hopSpinner1;
    private Spinner _hopSpinner2;
    private Spinner _hopSpinner3;
    private Spinner _hopSpinner4;
    private EditText _hopAdditionTime_1;
    private EditText _hopAdditionTime_2;
    private EditText _hopAdditionTime_3;
    private EditText _hopAdditionTime_4;
    private Spinner _yeast;
    private Disposable _recipeSubscription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null) {
            _recipeSubscription = ((MainActivity) getActivity()).getRecipe().subscribe(recipe -> {
                setBeerName(recipe.get_recipeName());

                setGrainSpinner1(recipe.get_fermentables().get(0).getName());
                setGrainInput1(String.format(Locale.getDefault(), "%.2f", recipe.get_fermentables().get(0).getAmount_lbs()));
                setGrainSpinner2(recipe.get_fermentables().get(1).getName());
                setGrainInput2(String.format(Locale.getDefault(), "%.2f", recipe.get_fermentables().get(1).getAmount_lbs()));
                setGrainSpinner3(recipe.get_fermentables().get(2).getName());
                setGrainInput3(String.format(Locale.getDefault(), "%.2f", recipe.get_fermentables().get(2).getAmount_lbs()));
                setGrainSpinner4(recipe.get_fermentables().get(3).getName());
                setGrainInput4(String.format(Locale.getDefault(), "%.2f", recipe.get_fermentables().get(3).getAmount_lbs()));

                setHopSpinner1(recipe.get_hops().get(0).getName());
                setHopInput1(String.format(Locale.getDefault(), "%.2f", recipe.get_hops().get(0).getAmount_oz()));
                setHopAdditionTime_1(Integer.toString(recipe.get_hops().get(0).getAdditionTime_min()));
                setHopSpinner2(recipe.get_hops().get(1).getName());
                setHopInput2(String.format(Locale.getDefault(), "%.2f", recipe.get_hops().get(1).getAmount_oz()));
                setHopAdditionTime_2(Integer.toString(recipe.get_hops().get(1).getAdditionTime_min()));
                setHopSpinner3(recipe.get_hops().get(2).getName());
                setHopInput3(String.format(Locale.getDefault(), "%.2f", recipe.get_hops().get(2).getAmount_oz()));
                setHopAdditionTime_3(Integer.toString(recipe.get_hops().get(2).getAdditionTime_min()));
                setHopSpinner4(recipe.get_hops().get(3).getName());
                setHopInput4(String.format(Locale.getDefault(), "%.2f", recipe.get_hops().get(3).getAmount_oz()));
                setHopAdditionTime_4(Integer.toString(recipe.get_hops().get(3).getAdditionTime_min()));

                setYeast(recipe.get_yeast());
            }, throwable -> Log.e("Recipe_Fragment", "Failed to get the recipe", throwable));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.recipe_fragment, container, false);

        _beerName = rootView.findViewById(R.id.name_input);
        _grainInput1 = rootView.findViewById(R.id.amount_input1);
        _grainInput2 = rootView.findViewById(R.id.amount_input2);
        _grainInput3 = rootView.findViewById(R.id.amount_input3);
        _grainInput4 = rootView.findViewById(R.id.amount_input4);
        _hopInput1 = rootView.findViewById(R.id.hop_amount_input1);
        _hopInput2 = rootView.findViewById(R.id.hop_amount_input2);
        _hopInput3 = rootView.findViewById(R.id.hop_amount_input3);
        _hopInput4 = rootView.findViewById(R.id.hop_amount_input4);
        _grainSpinner1 = rootView.findViewById(R.id.grain_spinner1);
        _grainSpinner2 = rootView.findViewById(R.id.grain_spinner2);
        _grainSpinner3 = rootView.findViewById(R.id.grain_spinner3);
        _grainSpinner4 = rootView.findViewById(R.id.grain_spinner4);
        _hopSpinner1 = rootView.findViewById(R.id.hop_spinner1);
        _hopSpinner2 = rootView.findViewById(R.id.hop_spinner2);
        _hopSpinner3 = rootView.findViewById(R.id.hop_spinner3);
        _hopSpinner4 = rootView.findViewById(R.id.hop_spinner4);
        _hopAdditionTime_1 = rootView.findViewById(R.id.addition_time1);
        _hopAdditionTime_2 = rootView.findViewById(R.id.addition_time2);
        _hopAdditionTime_3 = rootView.findViewById(R.id.addition_time3);
        _hopAdditionTime_4 = rootView.findViewById(R.id.addition_time4);
        _yeast = rootView.findViewById(R.id.yeast_spinner);

        return rootView;
    }

    @Override
    public void onDestroy() {
        _recipeSubscription.dispose();
        super.onDestroy();
    }

    public void setBeerName(String beerName) {
        _beerName.setText(beerName);
    }

    public String getBeerName() {
        return _beerName.getText().toString();
    }

    public void setGrainInput1(final String grainInput1) {
        _grainInput1.setText(grainInput1);
    }

    public String getGrainInput1() {
        return _grainInput1.getText().toString();
    }

    public void setGrainInput2(final String grainInput1) {
        _grainInput2.setText(grainInput1);
    }

    public String getGrainInput2() {
        return _grainInput2.getText().toString();
    }

    public void setGrainInput3(final String grainInput1) {
        _grainInput3.setText(grainInput1);
    }

    public String getGrainInput3() {
        return _grainInput3.getText().toString();
    }

    public void setGrainInput4(final String grainInput1) {
        _grainInput4.setText(grainInput1);
    }

    public String getGrainInput4() {
        return _grainInput4.getText().toString();
    }

    public void setGrainSpinner1(final String grain) {
        for (int i = 0; i < _grainSpinner1.getCount(); i++) {
            if (_grainSpinner1.getItemAtPosition(i).equals(grain)) {
                _grainSpinner1.setSelection(i);
                break;
            }
        }
    }

    public String getGrainSpinner1() {
        return _grainSpinner1.getSelectedItem().toString();
    }

    public void setGrainSpinner2(final String grain) {
        for (int i = 0; i < _grainSpinner2.getCount(); i++) {
            if (_grainSpinner2.getItemAtPosition(i).equals(grain)) {
                _grainSpinner2.setSelection(i);
                break;
            }
        }
    }

    public String getGrainSpinner2() {
        return _grainSpinner2.getSelectedItem().toString();
    }

    public void setGrainSpinner3(final String grain) {
        for (int i = 0; i < _grainSpinner3.getCount(); i++) {
            if (_grainSpinner3.getItemAtPosition(i).equals(grain)) {
                _grainSpinner3.setSelection(i);
                break;
            }
        }
    }

    public String getGrainSpinner3() {
        return _grainSpinner3.getSelectedItem().toString();
    }

    public void setGrainSpinner4(final String grain) {
        for (int i = 0; i < _grainSpinner4.getCount(); i++) {
            if (_grainSpinner4.getItemAtPosition(i).equals(grain)) {
                _grainSpinner4.setSelection(i);
                break;
            }
        }
    }

    public String getGrainSpinner4() {
        return _grainSpinner4.getSelectedItem().toString();
    }

    public String getHopInput1() {
        return _hopInput1.getText().toString();
    }

    public void setHopInput1(final String hopInput1) {
        _hopInput1.setText(hopInput1);
    }

    public String getHopInput2() {
        return _hopInput2.getText().toString();
    }

    public void setHopInput2(final String hopInput2) {
        _hopInput2.setText(hopInput2);
    }

    public String getHopInput3() {
        return _hopInput3.getText().toString();
    }

    public void setHopInput3(final String hopInput3) {
        _hopInput3.setText(hopInput3);
    }

    public String getHopInput4() {
        return _hopInput4.getText().toString();
    }

    public void setHopInput4(final String hopInput4) {
        _hopInput4.setText(hopInput4);
    }

    public String getHopSpinner1() {
        return _hopSpinner1.getSelectedItem().toString();
    }

    public void setHopSpinner1(final String hopSpinner1) {
        for (int i = 0; i < _hopSpinner1.getCount(); i++) {
            if (_hopSpinner1.getItemAtPosition(i).equals(hopSpinner1)) {
                _hopSpinner1.setSelection(i);
                break;
            }
        }
    }

    public String getHopSpinner2() {
        return _hopSpinner2.getSelectedItem().toString();
    }

    public void setHopSpinner2(final String hopSpinner2) {
        for (int i = 0; i < _hopSpinner2.getCount(); i++) {
            if (_hopSpinner2.getItemAtPosition(i).equals(hopSpinner2)) {
                _hopSpinner2.setSelection(i);
                break;
            }
        }
    }

    public String getHopSpinner3() {
        return _hopSpinner3.getSelectedItem().toString();
    }

    public void setHopSpinner3(final String hopSpinner3) {
        for (int i = 0; i < _hopSpinner3.getCount(); i++) {
            if (_hopSpinner3.getItemAtPosition(i).equals(hopSpinner3)) {
                _hopSpinner3.setSelection(i);
                break;
            }
        }
    }

    public String getHopSpinner4() {
        return _hopSpinner4.getSelectedItem().toString();
    }

    public void setHopSpinner4(final String hopSpinner4) {
        for (int i = 0; i < _hopSpinner4.getCount(); i++) {
            if (_hopSpinner4.getItemAtPosition(i).equals(hopSpinner4)) {
                _hopSpinner4.setSelection(i);
                break;
            }
        }
    }

    public String getHopAdditionTime_1() {
        return _hopAdditionTime_1.getText().toString();
    }

    public void setHopAdditionTime_1(final CharSequence text) {
        _hopAdditionTime_1.setText(text);
    }

    public String getHopAdditionTime_2() {
        return _hopAdditionTime_2.getText().toString();
    }

    public void setHopAdditionTime_2(final CharSequence text) {
        _hopAdditionTime_2.setText(text);
    }

    public String getHopAdditionTime_3() {
        return _hopAdditionTime_3.getText().toString();
    }

    public void setHopAdditionTime_3(final CharSequence text) {
        _hopAdditionTime_3.setText(text);
    }

    public String getHopAdditionTime_4() {
        return _hopAdditionTime_4.getText().toString();
    }

    public void setHopAdditionTime_4(final CharSequence text) {
        _hopAdditionTime_4.setText(text);
    }

    public String getYeast() {
        return _yeast.getSelectedItem().toString();
    }

    public void setYeast(final String yeast) {
        for (int i = 0; i < _yeast.getCount(); i++) {
            if (_yeast.getItemAtPosition(i).equals(yeast)) {
                _yeast.setSelection(i);
                break;
            }
        }
    }

    private void setSpinner(@IdRes final int viewId, final String text) {
        final Spinner spinner = getActivity().findViewById(viewId);
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(text)) {
                spinner.setSelection(i);
                break;
            }
        }
    }
}
