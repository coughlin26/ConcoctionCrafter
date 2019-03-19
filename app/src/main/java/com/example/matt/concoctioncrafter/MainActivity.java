/* Copyright Matthew Coughlin 2018, 2019 */

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

import com.example.matt.concoctioncrafter.data.Fermentable;
import com.example.matt.concoctioncrafter.data.Hop;
import com.example.matt.concoctioncrafter.data.Recipe;
import com.example.matt.concoctioncrafter.data.RecipeParcelable;
import com.example.matt.concoctioncrafter.data.RecipeViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.IdRes;
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

                    if (_recipeViewModel.findByName(recipe.get_recipeName()) != null) {
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
        final String beerName = getTextFromEditText(R.id.name_input);
        final String grain1 = getTextFromSpinner(R.id.grain_spinner1);
        final float grain1Amount = getFloatFromEditText(R.id.amount_input1);
        final String grain2 = getTextFromSpinner(R.id.grain_spinner2);
        final float grain2Amount = getFloatFromEditText(R.id.amount_input2);
        final String grain3 = getTextFromSpinner(R.id.grain_spinner3);
        final float grain3Amount = getFloatFromEditText(R.id.amount_input3);
        final String grain4 = getTextFromSpinner(R.id.grain_spinner4);
        final float grain4Amount = getFloatFromEditText(R.id.amount_input4);
        final String hop1 = getTextFromSpinner(R.id.hop_spinner1);
        final float hop1Amount = getFloatFromEditText(R.id.hop_amount_input1);
        final int hop1Time = getIntFromEditText(R.id.addition_time1);
        final String hop2 = getTextFromSpinner(R.id.hop_spinner2);
        final float hop2Amount = getFloatFromEditText(R.id.hop_amount_input2);
        final int hop2Time = getIntFromEditText(R.id.addition_time2);
        final String hop3 = getTextFromSpinner(R.id.hop_spinner3);
        final float hop3Amount = getFloatFromEditText(R.id.hop_amount_input3);
        final int hop3Time = getIntFromEditText(R.id.addition_time3);
        final String hop4 = getTextFromSpinner(R.id.hop_spinner4);
        final float hop4Amount = getFloatFromEditText(R.id.hop_amount_input4);
        final int hop4Time = getIntFromEditText(R.id.addition_time4);
        final String yeast = getTextFromSpinner(R.id.yeast_spinner);

        final List<Fermentable> fermentableList = new ArrayList<>();
        final List<Hop> hopList = new ArrayList<>();

        fermentableList.add(new Fermentable(grain1, grain1Amount));
        fermentableList.add(new Fermentable(grain2, grain2Amount));
        fermentableList.add(new Fermentable(grain3, grain3Amount));
        fermentableList.add(new Fermentable(grain4, grain4Amount));

        hopList.add(new Hop(hop1, hop1Amount, hop1Time));
        hopList.add(new Hop(hop2, hop2Amount, hop2Time));
        hopList.add(new Hop(hop3, hop3Amount, hop3Time));
        hopList.add(new Hop(hop4, hop4Amount, hop4Time));

        return new Recipe(beerName,
                "No Style",
                fermentableList,
                hopList,
                yeast);
    }

    private String getTextFromSpinner(@IdRes final int viewId) {
        return ((Spinner) findViewById(viewId)).getSelectedItem().toString();
    }

    private String getTextFromEditText(@IdRes final int viewId) {
        final String text = ((EditText) findViewById(viewId)).getText().toString();
        return text.isEmpty() ? "Nothing" : text;
    }

    private float getFloatFromEditText(@IdRes final int viewId) {
        final String text = getTextFromEditText(viewId);
        if (text.isEmpty()) {
            return 0f;
        } else {
            return Float.valueOf(text);
        }
    }

    private int getIntFromEditText(@IdRes final int viewId) {
        final String text = getTextFromEditText(viewId);
        if (text.isEmpty()) {
            return 0;
        } else {
            return Integer.valueOf(text);
        }
    }
}
