package com.example.matt.concoctioncrafter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matt.concoctioncrafter.data.Recipe;
import com.example.matt.concoctioncrafter.data.RecipeDatabase;
import com.example.matt.concoctioncrafter.data.RecipeParcelable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class BrewDayFragment extends Fragment {
    private Button _startBoilButton;
    private TextView _recipeName;
    private TextView _hop_type1;
    private TextView _hop_type2;
    private TextView _hop_type3;
    private TextView _hop_type4;
    private TextView _amount1;
    private TextView _amount2;
    private TextView _amount3;
    private TextView _amount4;
    private TextView _additionTime1;
    private TextView _additionTime2;
    private TextView _additionTime3;
    private TextView _additionTime4;

    private TextView _alcoholContent;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.brew_day_fragment, container, false);

        _startBoilButton = rootView.findViewById(R.id.start_boil_button);
        _startBoilButton.setOnClickListener(v -> Toast.makeText(getContext(), "This feature is coming soon!", Toast.LENGTH_SHORT).show());
        _recipeName = rootView.findViewById(R.id.name);
        _hop_type1 = rootView.findViewById(R.id.hop_type1);
        _hop_type2 = rootView.findViewById(R.id.hop_type2);
        _hop_type3 = rootView.findViewById(R.id.hop_type3);
        _hop_type4 = rootView.findViewById(R.id.hop_type4);
        _amount1 = rootView.findViewById(R.id.amount1);
        _amount2 = rootView.findViewById(R.id.amount2);
        _amount3 = rootView.findViewById(R.id.amount3);
        _amount4 = rootView.findViewById(R.id.amount4);
        _additionTime1 = rootView.findViewById(R.id.time1);
        _additionTime2 = rootView.findViewById(R.id.time2);
        _additionTime3 = rootView.findViewById(R.id.time3);
        _additionTime4 = rootView.findViewById(R.id.time4);
        _alcoholContent = rootView.findViewById(R.id.actual_ac);

        if (getArguments() != null) {
            final RecipeParcelable recipeParcelable = getArguments().getParcelable(MainActivity.RECIPE_KEY);

            if (recipeParcelable != null) {
                final Recipe recipe = recipeParcelable.getRecipe();

                if (recipe != null) {
                    setRecipeName(recipe.getRecipeName());
                    setHopType1(recipe.getHop_1());
                }
            }
        }

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                Toast.makeText(getContext(), "Saving is not available for Brew Day", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_import:
                RecipeDatabase.getDatabase(getContext()).beginTransaction();
                RecipeDatabase.getDatabase(getContext()).endTransaction();
                return true;
            case R.id.units_action:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public Button getStartBoilButton() {
        return _startBoilButton;
    }

    public void setStartBoilButton(final CharSequence text) {
        _startBoilButton.setText(text);
    }

    public String getRecipeName() {
        return _recipeName.getText().toString();
    }

    public void setRecipeName(final String name) {
        _recipeName.setText(name);
    }

    public String getHopType1() {
        return _hop_type1.getText().toString();
    }

    public void setHopType1(final String type) {
        _hop_type1.setText(type);
    }

    public TextView get_hop_type2() {
        return _hop_type2;
    }

    public void set_hop_type2(TextView _hop_type2) {
        this._hop_type2 = _hop_type2;
    }

    public TextView get_hop_type3() {
        return _hop_type3;
    }

    public void set_hop_type3(TextView _hop_type3) {
        this._hop_type3 = _hop_type3;
    }

    public TextView get_hop_type4() {
        return _hop_type4;
    }

    public void set_hop_type4(TextView _hop_type4) {
        this._hop_type4 = _hop_type4;
    }

    public TextView get_amount1() {
        return _amount1;
    }

    public void set_amount1(TextView _amount1) {
        this._amount1 = _amount1;
    }

    public TextView get_amount2() {
        return _amount2;
    }

    public void set_amount2(TextView _amount2) {
        this._amount2 = _amount2;
    }

    public TextView get_amount3() {
        return _amount3;
    }

    public void set_amount3(TextView _amount3) {
        this._amount3 = _amount3;
    }

    public TextView get_amount4() {
        return _amount4;
    }

    public void set_amount4(TextView _amount4) {
        this._amount4 = _amount4;
    }

    public TextView get_additionTime1() {
        return _additionTime1;
    }

    public void set_additionTime1(TextView _additionTime1) {
        this._additionTime1 = _additionTime1;
    }

    public TextView get_additionTime2() {
        return _additionTime2;
    }

    public void set_additionTime2(TextView _additionTime2) {
        this._additionTime2 = _additionTime2;
    }

    public TextView get_additionTime3() {
        return _additionTime3;
    }

    public void set_additionTime3(TextView _additionTime3) {
        this._additionTime3 = _additionTime3;
    }

    public TextView get_additionTime4() {
        return _additionTime4;
    }

    public void set_additionTime4(TextView _additionTime4) {
        this._additionTime4 = _additionTime4;
    }

    public TextView get_alcoholContent() {
        return _alcoholContent;
    }

    public void set_alcoholContent(TextView _alcoholContent) {
        this._alcoholContent = _alcoholContent;
    }
}
