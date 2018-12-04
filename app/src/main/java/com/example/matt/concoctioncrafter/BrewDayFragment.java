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
            _recipeSubscription = ((MainActivity) getActivity()).getRecipe().subscribe(recipe ->
            {
                setRecipeName(recipe.getRecipeName());
                setHopType1(recipe.getHop_1());
                setAmount1(Float.toString(recipe.getHop_1_amount()));
                setHopType2(recipe.getHop_2());
                setAmount2(Float.toString(recipe.getHop_2_amount()));
                setHopType3(recipe.getHop_3());
                setAmount3(Float.toString(recipe.getHop_3_amount()));
                setHopType4(recipe.getHop_4());
                setAmount4(Float.toString(recipe.getHop_4_amount()));
            });
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
                    setRecipeName(recipe.getRecipeName());
                    setHopType1(recipe.getHop_1());
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

    public void setAdditionTime3(TextView additionTime3) {
        _additionTime3 = _additionTime3;
    }

    public String getAdditionTime4() {
        return _additionTime4.getText().toString();
    }

    public void setAdditionTime4(final String additionTime4) {
        _additionTime4.setText(additionTime4);
    }

    public float getAlcoholContent() {
        return Float.valueOf(_alcoholContent.getText().toString());
    }

    public void setAlcoholContent(final float alcoholContent) {
        _alcoholContent.setText(String.format(Locale.getDefault(), "%.2f", alcoholContent));
    }
}
