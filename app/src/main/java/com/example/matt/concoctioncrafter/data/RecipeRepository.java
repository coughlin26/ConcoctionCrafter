/* Copyright Matthew Coughlin 2018, 2019 */

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

    protected RecipeRepository(Application application) {
        RecipeDatabase db = RecipeDatabase.getDatabase(application);
        _recipeDao = db.recipeDAO();
        _allRecipes = _recipeDao.getAllRecipes();
    }

    List<Recipe> getAll() {
        try {
            return new GetAllAsyncTask(_recipeDao).execute().get();
        } catch (final InterruptedException | ExecutionException e) {
            Log.e("RecipeRepository", "Failed to get all recipes", e);
            return null;
        }
    }

    LiveData<List<Recipe>> getAllRecipes() {
        return _allRecipes;
    }

    void insert(Recipe recipe) {
        new InsertAsyncTask(_recipeDao).execute(recipe);
    }

    void insert(Recipe... recipes) {
        new InsertAsyncTask(_recipeDao).execute(recipes);
    }

    void update(Recipe recipe) {
        new UpdateAsyncTask(_recipeDao).execute(recipe);
    }

    void update(Recipe... recipes) {
        new UpdateAsyncTask(_recipeDao).execute(recipes);
    }

    Recipe findByName(String name) {
        try {
            return new FindByNameAsyncTask(_recipeDao).execute(name).get();
        } catch (final InterruptedException | ExecutionException e) {
            Log.e("RecipeRepository", "findByName interrupted", e);
            return null;
        }
    }

    void delete(Recipe recipe) {
        new DeleteAsyncTask(_recipeDao).execute(recipe);
    }

    void deleteAll(Recipe... recipes) {
        new DeleteAsyncTask(_recipeDao).execute(recipes);
    }

    private static class InsertAsyncTask extends AsyncTask<Recipe, Void, Void> {
        private RecipeDAO _asyncRecipeDao;

        InsertAsyncTask(RecipeDAO recipeDAO) {
            _asyncRecipeDao = recipeDAO;
        }

        @Override
        protected Void doInBackground(final Recipe... params) {
            Log.d("Testing", "Inserting in AsyncTask...");
            _asyncRecipeDao.insert(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Recipe, Void, Void> {
        private RecipeDAO _asyncRecipeDao;

        DeleteAsyncTask(RecipeDAO recipeDAO) {
            _asyncRecipeDao = recipeDAO;
        }

        @Override
        protected Void doInBackground(final Recipe... params) {
            Log.d("Testing", "Deleting in AsyncTask...");
            if (params.length == 1) {
                _asyncRecipeDao.delete(params[0]);
            } else {
                _asyncRecipeDao.deleteAll();
            }
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Recipe, Void, Void> {
        private RecipeDAO _asyncRecipeDao;

        UpdateAsyncTask(RecipeDAO recipeDAO) {
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

    private static class FindByNameAsyncTask extends AsyncTask<String, Void, Recipe> {
        private RecipeDAO _asyncRecipeDao;

        FindByNameAsyncTask(RecipeDAO recipeDAO) {
            _asyncRecipeDao = recipeDAO;
        }

        @Override
        protected Recipe doInBackground(final String... params) {
            Log.d("Testing", "Querying for recipe: " + params[0]);
            return _asyncRecipeDao.findByName(params[0]);
        }
    }

    private static class GetAllAsyncTask extends AsyncTask<Void, Void, List<Recipe>> {
        private RecipeDAO _asyncRecipeDao;

        GetAllAsyncTask(RecipeDAO recipeDAO) {
            _asyncRecipeDao = recipeDAO;
        }

        @Override
        protected List<Recipe> doInBackground(final Void... params) {
            return _asyncRecipeDao.getAll();
        }
    }
}
