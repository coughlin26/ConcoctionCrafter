/* Copyright Matthew Coughlin 2018, 2019 */

package com.example.matt.concoctioncrafter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matt.concoctioncrafter.data.Recipe;
import com.example.matt.concoctioncrafter.data.RecipeParcelable;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import io.reactivex.disposables.Disposable;

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
    private Disposable _recipeSubscription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null) {
            _recipeSubscription = ((MainActivity) getActivity()).getRecipe().subscribe(this::importRecipe);
        }
    }

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
                    importRecipe(recipe);
                }
            }
        }

        return rootView;
    }

    @Override
    public void onDestroy() {
        _recipeSubscription.dispose();
        super.onDestroy();
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

    public String getHopType2() {
        return _hop_type2.getText().toString();
    }

    public void setHopType2(String hop_type2) {
        _hop_type2.setText(hop_type2);
    }

    public String getHopType3() {
        return _hop_type3.getText().toString();
    }

    public void setHopType3(final String hop_type3) {
        _hop_type3.setText(hop_type3);
    }

    public String getHopType4() {
        return _hop_type4.getText().toString();
    }

    public void setHopType4(final String hop_type4) {
        _hop_type4.setText(hop_type4);
    }

    public String getAmount1() {
        return _amount1.getText().toString();
    }

    public void setAmount1(final String amount1) {
        _amount1.setText(amount1);
    }

    public String getAmount2() {
        return _amount2.getText().toString();
    }

    public void setAmount2(final String amount2) {
        _amount2.setText(amount2);
    }

    public String getAmount3() {
        return _amount3.getText().toString();
    }

    public void setAmount3(final String amount3) {
        _amount3.setText(amount3);
    }

    public String getAmount4() {
        return _amount4.getText().toString();
    }

    public void setAmount4(final String amount4) {
        _amount4.setText(amount4);
    }

    public String getAdditionTime1() {
        return _additionTime1.getText().toString();
    }

    public void setAdditionTime1(final String additionTime1) {
        _additionTime1.setText(additionTime1);
    }

    public String getAdditionTime2() {
        return _additionTime2.getText().toString();
    }

    public void setAdditionTime2(final String additionTime2) {
        _additionTime2.setText(additionTime2);
    }

    public String getAdditionTime3() {
        return _additionTime3.getText().toString();
    }

    public void setAdditionTime3(final String additionTime3) {
        _additionTime3 = _additionTime3;
    }

    public String getAdditionTime4() {
        return _additionTime4.getText().toString();
    }

    public void setAdditionTime4(final String additionTime4) {
        _additionTime4.setText(additionTime4);
    }

    private void importRecipe(final Recipe recipe) {
        setRecipeName(recipe.get_recipeName());

        setHopType1(recipe.get_hops().get(0).getName());
        setAmount1(Float.toString(recipe.get_hops().get(0).getAmount()));
        setAdditionTime1(Integer.toString(recipe.get_hops().get(0).getAdditionTime_min()));
        setHopType2(recipe.get_hops().get(1).getName());
        setAmount2(Float.toString(recipe.get_hops().get(1).getAmount()));
        setAdditionTime2(Integer.toString(recipe.get_hops().get(1).getAdditionTime_min()));
        setHopType3(recipe.get_hops().get(2).getName());
        setAmount3(Float.toString(recipe.get_hops().get(2).getAmount()));
        setAdditionTime3(Integer.toString(recipe.get_hops().get(2).getAdditionTime_min()));
        setHopType4(recipe.get_hops().get(3).getName());
        setAmount4(Float.toString(recipe.get_hops().get(3).getAmount()));
        setAdditionTime4(Integer.toString(recipe.get_hops().get(3).getAdditionTime_min()));
    }

    public float getAlcoholContent() {
        return Float.valueOf(_alcoholContent.getText().toString());
    }

    public void setAlcoholContent(final float alcoholContent) {
        _alcoholContent.setText(String.format(Locale.getDefault(), "%.2f", alcoholContent));
    }
}
