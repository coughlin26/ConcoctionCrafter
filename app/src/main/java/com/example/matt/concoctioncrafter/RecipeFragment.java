package com.example.matt.concoctioncrafter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

public class RecipeFragment extends Fragment {
    private EditText beerName;
    private EditText grainInput1;
    private EditText grainInput2;
    private EditText grainInput3;
    private EditText grainInput4;
    private EditText hopInput1;
    private EditText hopInput2;
    private EditText hopInput3;
    private EditText hopInput4;

    private Spinner grainSpinner1;
    private Spinner grainSpinner2;
    private Spinner grainSpinner3;
    private Spinner grainSpinner4;
    private Spinner hopSpinner1;
    private Spinner hopSpinner2;
    private Spinner hopSpinner3;
    private Spinner hopSpinner4;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.recipe_fragment, container, false);

        beerName = rootView.findViewById(R.id.name_input);
        grainInput1 = rootView.findViewById(R.id.amount_input1);
        grainInput2 = rootView.findViewById(R.id.amount_input2);
        grainInput3 = rootView.findViewById(R.id.amount_input3);
        grainInput4 = rootView.findViewById(R.id.amount_input4);
        hopInput1 = rootView.findViewById(R.id.hop_amount_input1);
        hopInput2 = rootView.findViewById(R.id.hop_amount_input2);
        hopInput3 = rootView.findViewById(R.id.hop_amount_input3);
        hopInput4 = rootView.findViewById(R.id.hop_amount_input4);

        grainSpinner1 = rootView.findViewById(R.id.grain_spinner1);
        grainSpinner2 = rootView.findViewById(R.id.grain_spinner2);
        grainSpinner3 = rootView.findViewById(R.id.grain_spinner3);
        grainSpinner4 = rootView.findViewById(R.id.grain_spinner4);
        hopSpinner1 = rootView.findViewById(R.id.hop_spinner1);
        hopSpinner2 = rootView.findViewById(R.id.hop_spinner2);
        hopSpinner3 = rootView.findViewById(R.id.hop_spinner3);
        hopSpinner4 = rootView.findViewById(R.id.hop_spinner4);

        return rootView;
    }
}
