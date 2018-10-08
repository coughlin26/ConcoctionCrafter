package com.example.matt.concoctioncrafter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class BrewDayFragment extends Fragment {
    private Button _startBoilButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.brew_day_fragment, container, false);

        _startBoilButton = rootView.findViewById(R.id.start_boil_button);
        _startBoilButton.setOnClickListener(v -> Toast.makeText(getContext(), "This feature is coming soon!", Toast.LENGTH_SHORT).show());

        return rootView;
    }
}
