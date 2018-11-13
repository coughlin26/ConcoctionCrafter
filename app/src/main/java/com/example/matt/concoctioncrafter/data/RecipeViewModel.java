/* Copyright Matthew Coughlin 2018 */

package com.example.matt.concoctioncrafter.data;

import android.app.Application;
import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class RecipeViewModel extends AndroidViewModel {
    public static final String TAG = "Recipe_View_Model";
    private RecipeRepository _recipeRepository;
    private LiveData<List<Recipe>> _recipeLiveData;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        _recipeRepository = new RecipeRepository(application);
        _recipeLiveData = _recipeRepository.getAllRecipes();
    }

    public LiveData<List<Recipe>> getRecipeList() {
        return _recipeLiveData;
    }

    public void insert(Recipe recipe) {
        Log.d(TAG, "Inserting recipe: " + recipe.getRecipeName());
        _recipeRepository.insert(recipe);
    }

    public void insert(Recipe... recipes) {
        Log.d(TAG, "Inserting recipes, starting with: " + recipes[0].getRecipeName());
        _recipeRepository.insert(recipes);
    }

    public void update(Recipe recipe) {
        Log.d(TAG, "Updating recipe named: " + recipe.getRecipeName());
        _recipeRepository.update(recipe);
    }

    public void update(Recipe... recipes) {
        Log.d(TAG, "Updating recipes, stating with: " + recipes[0].getRecipeName());
        _recipeRepository.update(recipes);
    }

    public Recipe findByName(String name) {
        Log.d(TAG, "Searching for " + name);
        return _recipeRepository.findByName(name);
    }

    public void delete(final Recipe recipe) {
        Log.d(TAG, "Deleting recipe named: " + recipe.getRecipeName());
        _recipeRepository.delete(recipe);
    }

    public void deleteAll() {
        Log.d(TAG, "Deleting all recipes...");
        _recipeRepository.deleteAll();
    }
}
