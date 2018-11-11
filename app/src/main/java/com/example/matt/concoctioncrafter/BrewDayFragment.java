package com.example.matt.concoctioncrafter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matt.concoctioncrafter.data.RecipeDatabase;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class BrewDayFragment extends Fragment {
    private Button _startBoilButton;
    public TextView _recipeName;
    public TextView _hop_type1;
    public TextView _hop_type2;
    public TextView _hop_type3;
    public TextView _hop_type4;
    public TextView _amount1;
    public TextView _amount2;
    public TextView _amount3;
    public TextView _amount4;
    public TextView _additionTime1;
    public TextView _additionTime2;
    public TextView _additionTime3;
    public TextView _additionTime4;

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
}
