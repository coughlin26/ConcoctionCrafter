/* Copyright Matthew Coughlin 2018 */

package com.example.matt.concoctioncrafter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.matt.concoctioncrafter.data.Recipe;
import com.example.matt.concoctioncrafter.data.RecipeDAO;
import com.example.matt.concoctioncrafter.data.RecipeDatabase;
import com.example.matt.concoctioncrafter.data.RecipeViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class RecipeFragment extends Fragment {
    private RecipeViewModel recipeViewModel;

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
    private Spinner yeast;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

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
        yeast = rootView.findViewById(R.id.yeast_spinner);

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                final Recipe recipe = new Recipe(beerName.getText().toString(),
                        grainSpinner1.getSelectedItem().toString(),
                        Float.valueOf(grainInput1.getText().toString()),
                        grainSpinner2.getSelectedItem().toString(),
                        Float.valueOf(grainInput2.getText().toString()),
                        grainSpinner3.getSelectedItem().toString(),
                        Float.valueOf(grainInput3.getText().toString()),
                        grainSpinner4.getSelectedItem().toString(),
                        Float.valueOf(grainInput4.getText().toString()),
                        hopSpinner1.getSelectedItem().toString(),
                        Float.valueOf(hopInput1.getText().toString()),
                        hopSpinner2.getSelectedItem().toString(),
                        Float.valueOf(hopInput2.getText().toString()),
                        hopSpinner3.getSelectedItem().toString(),
                        Float.valueOf(hopInput3.getText().toString()),
                        hopSpinner4.getSelectedItem().toString(),
                        Float.valueOf(hopInput4.getText().toString()),
                        yeast.getSelectedItem().toString());

                final RecipeDAO recipeDAO = RecipeDatabase.getDatabase(getContext()).recipeDAO();

                if (recipeDAO.findByName(beerName.getText().toString()) != null) {
                    recipeDAO.update(recipe);
                } else {
                    recipeDAO.insert(recipe);
                }
                return true;
            case R.id.action_import:
                Toast.makeText(getContext(), "Import coming soon", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.units_action:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initData() {
        recipeViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(RecipeViewModel.class);
    }

    public void removeData() {
        if (recipeViewModel != null) {
            recipeViewModel.deleteAll();
        }
    }
}
