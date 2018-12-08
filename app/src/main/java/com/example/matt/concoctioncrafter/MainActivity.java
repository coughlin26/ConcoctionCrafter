/* Copyright Matthew Coughlin 2018 */

package com.example.matt.concoctioncrafter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.matt.concoctioncrafter.data.Recipe;
import com.example.matt.concoctioncrafter.data.RecipeParcelable;
import com.example.matt.concoctioncrafter.data.RecipeViewModel;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 7;
    public static final String RECIPE_KEY = "RECIPE_KEY";
    private static final int NUM_PAGES = 2;
    private ViewPager _pager;
    private FragmentPagerAdapter _pageAdapter;
    private TabLayout _tabLayout;
    private Toolbar _toolBar;
    private RecipeViewModel _recipeViewModel;
    private PublishSubject<Recipe> _recipe = PublishSubject.create();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _pager = findViewById(R.id.pager);
        _tabLayout = findViewById(R.id.pager_header);
        _toolBar = findViewById(R.id.toolbar);
        _pageAdapter = new CustomPageAdapter(getSupportFragmentManager());
        _pager.setAdapter(_pageAdapter);
        _recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        _tabLayout.setupWithViewPager(_pager);
        setSupportActionBar(_toolBar);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.getParcelableExtra(RECIPE_KEY) != null) {
                if (((RecipeParcelable) data.getParcelableExtra(RECIPE_KEY)).getRecipe() != null) {
                    _recipe.onNext(((RecipeParcelable) data.getParcelableExtra(RECIPE_KEY)).getRecipe());
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Provides a recipe observable to populate the recipe and brew day fragments.
     *
     * @return The current recipe being viewed
     */
    public Subject<Recipe> getRecipe() {
        return _recipe;
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.title_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                if (_pager.getCurrentItem() == 0) {
                    final Recipe recipe = makeRecipe();

                    if (_recipeViewModel.findByName(recipe.getRecipeName()) != null) {
                        Log.d("Testing", "Updating the recipe");
                        _recipeViewModel.update(recipe);
                    } else {
                        Log.d("Testing", "Inserting a new recipe");
                        _recipeViewModel.insert(recipe);
                    }
                } else if (_pager.getCurrentItem() == 1) {
                    Toast.makeText(this, "Cannot save a recipe on Brew Day", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Invalid page: " + _pager.getCurrentItem(), Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.action_import:
                final Intent chooserIntent = new Intent(this, ChooseRecipeActivity.class);
                startActivityForResult(chooserIntent, REQUEST_CODE);
                return true;
            case R.id.action_delete:
                _recipeViewModel.deleteAll();
                return true;
            case R.id.settings_action:
                final Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (_pager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            _pager.setCurrentItem(_pager.getCurrentItem() - 1);
        }
    }

    /**
     * A custom page adapter.
     */
    private class CustomPageAdapter extends FragmentPagerAdapter {
        private CustomPageAdapter(final FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new RecipeFragment();
            } else {
                return new BrewDayFragment();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public CharSequence getPageTitle(final int position) {
            if (position == 0) {
                return getString(R.string.recipe);
            } else {
                return getString(R.string.brew_day);
            }
        }
    }

    /**
     * Creates a new Recipe from the views in the Recipe fragment.
     */
    private Recipe makeRecipe() {
        final String beerName = ((EditText) findViewById(R.id.name_input)).getText().toString();
        final String grain1 = ((Spinner) findViewById(R.id.grain_spinner1)).getSelectedItem().toString();
        final String grain1Amount = ((EditText) findViewById(R.id.amount_input1)).getText().toString();
        final String grain2 = ((Spinner) findViewById(R.id.grain_spinner2)).getSelectedItem().toString();
        final String grain2Amount = ((EditText) findViewById(R.id.amount_input2)).getText().toString();
        final String grain3 = ((Spinner) findViewById(R.id.grain_spinner3)).getSelectedItem().toString();
        final String grain3Amount = ((EditText) findViewById(R.id.amount_input3)).getText().toString();
        final String grain4 = ((Spinner) findViewById(R.id.grain_spinner4)).getSelectedItem().toString();
        final String grain4Amount = ((EditText) findViewById(R.id.amount_input4)).getText().toString();
        final String hop1 = ((Spinner) findViewById(R.id.hop_spinner1)).getSelectedItem().toString();
        final String hop1Amount = ((EditText) findViewById(R.id.hop_amount_input1)).getText().toString();
        final String hop2 = ((Spinner) findViewById(R.id.hop_spinner2)).getSelectedItem().toString();
        final String hop2Amount = ((EditText) findViewById(R.id.hop_amount_input2)).getText().toString();
        final String hop3 = ((Spinner) findViewById(R.id.hop_spinner3)).getSelectedItem().toString();
        final String hop3Amount = ((EditText) findViewById(R.id.hop_amount_input3)).getText().toString();
        final String hop4 = ((Spinner) findViewById(R.id.hop_spinner4)).getSelectedItem().toString();
        final String hop4Amount = ((EditText) findViewById(R.id.hop_amount_input4)).getText().toString();
        final String yeast = ((Spinner) findViewById(R.id.yeast_spinner)).getSelectedItem().toString();


        return new Recipe(beerName == null || "".equals(beerName) ? "Unnamed Beer" : beerName,
                grain1,
                grain1Amount.isEmpty() ? 0f : Float.valueOf(grain1Amount),
                grain2,
                grain2Amount.isEmpty() ? 0f : Float.valueOf(grain2Amount),
                grain3,
                grain3Amount.isEmpty() ? 0f : Float.valueOf(grain3Amount),
                grain4,
                grain4Amount.isEmpty() ? 0f : Float.valueOf(grain4Amount),
                hop1,
                hop1Amount.isEmpty() ? 0f : Float.valueOf(hop1Amount),
                hop2,
                hop2Amount.isEmpty() ? 0f : Float.valueOf(hop2Amount),
                hop3,
                hop3Amount.isEmpty() ? 0f : Float.valueOf(hop3Amount),
                hop4,
                hop4Amount.isEmpty() ? 0f : Float.valueOf(hop4Amount),
                yeast);
    }
}
