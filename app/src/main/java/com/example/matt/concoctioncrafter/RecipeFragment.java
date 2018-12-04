/* Copyright Matthew Coughlin 2018 */

package com.example.matt.concoctioncrafter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
    private Spinner _yeast;
    private Disposable _recipeSubscription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null) {
            _recipeSubscription = ((MainActivity) getActivity()).getRecipe().subscribe(recipe ->
            {
                setBeerName(recipe.getRecipeName());
                setGrainSpinner1(recipe.getGrain_1());
                setGrainInput1(String.format(Locale.getDefault(), "%.2f", recipe.getGrain_1_amount()));
                setGrainSpinner2(recipe.getGrain_2());
                setGrainInput2(String.format(Locale.getDefault(), "%.2f", recipe.getGrain_2_amount()));
                setGrainSpinner3(recipe.getGrain_3());
                setGrainInput3(String.format(Locale.getDefault(), "%.2f", recipe.getGrain_3_amount()));
                setGrainSpinner4(recipe.getGrain_4());
                setGrainInput4(String.format(Locale.getDefault(), "%.2f", recipe.getGrain_4_amount()));
                setHopSpinner1(recipe.getHop_1());
                setHopInput1(String.format(Locale.getDefault(), "%.2f", recipe.getHop_1_amount()));
                setHopSpinner2(recipe.getHop_2());
                setHopInput2(String.format(Locale.getDefault(), "%.2f", recipe.getHop_2_amount()));
                setHopSpinner3(recipe.getHop_3());
                setHopInput3(String.format(Locale.getDefault(), "%.2f", recipe.getHop_3_amount()));
                setHopSpinner4(recipe.getHop_4());
                setHopInput4(String.format(Locale.getDefault(), "%.2f", recipe.getHop_4_amount()));
                setYeast(recipe.getYeast());
            });
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
}
