/* Copyright Matthew Coughlin 2018 */

package com.example.matt.concoctioncrafter.data;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class RecipeViewModel extends AndroidViewModel {
    private RecipeDAO recipeDAO;
    private LiveData<List<Recipe>> recipeLiveData;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        recipeDAO = RecipeDatabase.getDatabase(application).recipeDAO();
    }

    public LiveData<List<Recipe>> getRecipeList() {
        return recipeLiveData;
    }

    public void insert(Recipe... recipes) {
        recipeDAO.insert(recipes);
    }

    public void update(Recipe recipe) {
        recipeDAO.update(recipe);
    }

    public void deleteAll() {
        recipeDAO.deleteAll();
    }
}
