package com.example.matt.concoctioncrafter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.matt.concoctioncrafter.data.RecipeDatabase;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class BrewDayFragment extends Fragment {
    private Button _startBoilButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.brew_day_fragment, container, false);

        _startBoilButton = rootView.findViewById(R.id.start_boil_button);
        _startBoilButton.setOnClickListener(v -> Toast.makeText(getContext(), "This feature is coming soon!", Toast.LENGTH_SHORT).show());

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
