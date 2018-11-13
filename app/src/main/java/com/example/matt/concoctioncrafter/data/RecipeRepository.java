/* Copyright Matthew Coughlin 2018 */

package com.example.matt.concoctioncrafter.data;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class RecipeRepository {
    private RecipeDAO _recipeDao;
    private LiveData<List<Recipe>> _allRecipes;

    public RecipeRepository(Application application) {
        RecipeDatabase db = RecipeDatabase.getDatabase(application);
        _recipeDao = db.recipeDAO();
        _allRecipes = _recipeDao.getAllRecipes();
    }

    LiveData<List<Recipe>> getAllRecipes() {
        return _allRecipes;
    }

    void insert(Recipe recipe) {
        new insertAsyncTask(_recipeDao).execute(recipe);
    }

    void insert(Recipe... recipes) {
        new insertAsyncTask(_recipeDao).execute(recipes);
    }

    void update(Recipe recipe) {
        new updateAsyncTask(_recipeDao).execute(recipe);
    }

    void update(Recipe... recipes) {
        new updateAsyncTask(_recipeDao).execute(recipes);
    }

    Recipe findByName(String name) {
        try {
            return new queryAsyncTask(_recipeDao).execute(name).get();
        } catch (final InterruptedException | ExecutionException e) {
            Log.e("RecipeRepository", "findByName interrupted", e);
            return null;
        }
    }

    void delete(Recipe recipe) {
        new deleteAsyncTask(_recipeDao).execute(recipe);
    }

    void deleteAll(Recipe... recipes) {
        new deleteAsyncTask(_recipeDao).execute(recipes);
    }

    private static class insertAsyncTask extends AsyncTask<Recipe, Void, Void> {
        private RecipeDAO _asyncRecipeDao;

        insertAsyncTask(RecipeDAO recipeDAO) {
            _asyncRecipeDao = recipeDAO;
        }

        // Why isn't this working?
        @Override
        protected Void doInBackground(final Recipe... params) {
            Log.d("Testing", "Inserting in AsyncTask...");
            _asyncRecipeDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Recipe, Void, Void> {
        private RecipeDAO _asyncRecipeDao;

        deleteAsyncTask(RecipeDAO recipeDAO) {
            _asyncRecipeDao = recipeDAO;
        }

        @Override
        protected Void doInBackground(final Recipe... params) {
            Log.d("Testing", "Deleting in AsyncTask...");
            if (params.length > 1) {
                _asyncRecipeDao.delete(params[0]);
            } else {
                _asyncRecipeDao.deleteAll();
            }
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Recipe, Void, Void> {
        private RecipeDAO _asyncRecipeDao;

        updateAsyncTask(RecipeDAO recipeDAO) {
            _asyncRecipeDao = recipeDAO;
        }

        @Override
        protected Void doInBackground(final Recipe... params) {
            Log.d("Testing", "Updating in AsyncTask...");
            for (final Recipe recipe : params) {
                _asyncRecipeDao.update(recipe);
            }
            return null;
        }
    }

    private static class queryAsyncTask extends AsyncTask<String, Void, Recipe> {
        private RecipeDAO _asyncRecipeDao;

        queryAsyncTask(RecipeDAO recipeDAO) {
            _asyncRecipeDao = recipeDAO;
        }

        @Override
        protected Recipe doInBackground(final String... params) {
            Log.d("Testing", "Querying for recipe: " + params[0]);
            return _asyncRecipeDao.findByName(params[0]);
        }
    }
}
