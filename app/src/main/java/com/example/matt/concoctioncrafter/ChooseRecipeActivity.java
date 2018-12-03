/* Copyright Matthew Coughlin 2018 */

package com.example.matt.concoctioncrafter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.matt.concoctioncrafter.data.RecipeParcelable;
import com.example.matt.concoctioncrafter.data.RecipeViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.disposables.Disposable;

public class ChooseRecipeActivity extends AppCompatActivity {
    private RecyclerView _recipeList;
    private RecipeListAdapter _recipeAdapter;
    private Disposable _recipeClickSubscription;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_chooser);

        final RecipeViewModel recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        _recipeList = findViewById(R.id.recipe_list);
        _recipeList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        _recipeAdapter = new RecipeListAdapter(recipeViewModel.getAll());
        _recipeList.setAdapter(_recipeAdapter);

        _recipeClickSubscription = _recipeAdapter.getRecipeClicks().subscribe(recipe -> {
            Toast.makeText(this, "Clicked on " + recipe.getRecipeName(), Toast.LENGTH_SHORT).show();
            final Intent intent = new Intent();
            intent.putExtra(MainActivity.RECIPE_KEY, new RecipeParcelable(recipe));
            setResult(RESULT_OK, intent);
            finish();
        });

        setSupportActionBar(findViewById(R.id.toolbar));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        _recipeClickSubscription.dispose();
        super.onDestroy();
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
