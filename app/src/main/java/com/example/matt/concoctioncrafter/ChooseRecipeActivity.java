/* Copyright Matthew Coughlin 2018 */

package com.example.matt.concoctioncrafter;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.matt.concoctioncrafter.data.RecipeViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChooseRecipeActivity extends AppCompatActivity {
    private RecyclerView _recipeList;
    private RecipeListAdapter _recipeAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_chooser);

        final RecipeViewModel recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        _recipeList = findViewById(R.id.recipe_list);
        _recipeList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        _recipeAdapter = new RecipeListAdapter(recipeViewModel.getAll());
        _recipeList.setAdapter(_recipeAdapter);
        setSupportActionBar(findViewById(R.id.toolbar));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(final Menu menu) {
        return true;
    }
}
