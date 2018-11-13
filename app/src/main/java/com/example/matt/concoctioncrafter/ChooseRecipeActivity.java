/* Copyright Matthew Coughlin 2018 */

package com.example.matt.concoctioncrafter;

import android.os.Bundle;

import com.example.matt.concoctioncrafter.data.RecipeViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChooseRecipeActivity extends AppCompatActivity {
    private RecyclerView _recipeList;
    private RecipeListAdapter _recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_chooser);

        final RecipeViewModel recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        _recipeList = findViewById(R.id.recipe_list);
        _recipeList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        _recipeAdapter = new RecipeListAdapter(recipeViewModel.getAll());
        _recipeList.setAdapter(_recipeAdapter);
    }
}
