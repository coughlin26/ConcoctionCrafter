/* Copyright Matthew Coughlin 2018 */

package com.example.matt.concoctioncrafter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public void setBeerName(String beerName) {
        _beerName.setText(beerName);
    }

    public String getBeerName() {
        return _beerName.getText().toString();
    }

    public String getGrainInput1() {
        return _grainInput1.getText().toString();
    }
}
