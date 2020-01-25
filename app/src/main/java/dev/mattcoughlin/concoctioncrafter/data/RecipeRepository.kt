/* Copyright Matthew Coughlin 2018, 2019, 2020 */

package dev.mattcoughlin.concoctioncrafter.data

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.concurrent.ExecutionException

class RecipeRepository(application: Application) {
    private val _recipeDao: RecipeDAO
    internal val allRecipes: LiveData<List<Recipe>>
    private val _mutableLiveData = MutableLiveData<List<Recipe>>()

    internal val all: List<Recipe>?
        get() {
            var ret: List<Recipe>?
            try {
                ret = GetAllAsyncTask(_recipeDao).execute().get()
            } catch (e: InterruptedException) {
                Log.e("RecipeRepository", "Failed to get all recipes", e)
                ret = null
            } catch (e: ExecutionException) {
                Log.e("RecipeRepository", "Failed to get all recipes", e)
                ret = null
            }
            return ret
        }

    init {
        val db = RecipeDatabase.getDatabase(application)
        _recipeDao = db.recipeDAO()
        allRecipes = _recipeDao.allRecipes
        updateList()
    }

    private fun updateList() {
        _mutableLiveData.postValue(allRecipes.value)
    }

    internal fun insert(recipe: Recipe) {
        InsertAsyncTask(_recipeDao).execute(recipe)
        updateList()
    }

    internal fun insert(vararg recipes: Recipe) {
        InsertAsyncTask(_recipeDao).execute(*recipes)
        updateList()
    }

    internal fun update(recipe: Recipe) {
        UpdateAsyncTask(_recipeDao).execute(recipe)
        updateList()
    }

    internal fun update(vararg recipes: Recipe) {
        UpdateAsyncTask(_recipeDao).execute(*recipes)
        updateList()
    }

    internal fun findByName(name: String): Recipe? {
        var ret: Recipe?
        try {
            ret = FindByNameAsyncTask(_recipeDao).execute(name).get()
        } catch (e: InterruptedException) {
            Log.e("RecipeRepository", "findByName interrupted", e)
            ret = null
        } catch (e: ExecutionException) {
            Log.e("RecipeRepository", "findByName interrupted", e)
            ret = null
        }
        return ret
    }

    internal fun delete(recipe: Recipe) {
        DeleteAsyncTask(_recipeDao).execute(recipe)
        updateList()
    }

    internal fun deleteAll(vararg recipes: Recipe) {
        DeleteAsyncTask(_recipeDao).execute(*recipes)
        updateList()
    }

    private class InsertAsyncTask internal constructor(private val _asyncRecipeDao: RecipeDAO) : AsyncTask<Recipe, Void, Void>() {

        override fun doInBackground(vararg params: Recipe): Void? {
            Log.d("Testing", "Inserting in AsyncTask...")
            _asyncRecipeDao.insert(params[0])
            return null
        }
    }

    private class DeleteAsyncTask internal constructor(private val _asyncRecipeDao: RecipeDAO) : AsyncTask<Recipe, Void, Void>() {

        override fun doInBackground(vararg params: Recipe): Void? {
            Log.d("Testing", "Deleting in AsyncTask...")
            if (params.size == 1) {
                _asyncRecipeDao.delete(params[0])
            } else {
                _asyncRecipeDao.deleteAll()
            }
            return null
        }
    }

    private class UpdateAsyncTask internal constructor(private val _asyncRecipeDao: RecipeDAO) : AsyncTask<Recipe, Void, Void>() {

        override fun doInBackground(vararg params: Recipe): Void? {
            Log.d("Testing", "Updating in AsyncTask...")
            for (recipe in params) {
                _asyncRecipeDao.update(recipe)
            }
            return null
        }
    }

    private class FindByNameAsyncTask internal constructor(private val _asyncRecipeDao: RecipeDAO) : AsyncTask<String, Void, Recipe>() {

        override fun doInBackground(vararg params: String): Recipe {
            Log.d("Testing", "Querying for recipe: " + params[0])
            return _asyncRecipeDao.findByName(params[0])
        }
    }

    private class GetAllAsyncTask internal constructor(private val _asyncRecipeDao: RecipeDAO) : AsyncTask<Void, Void, List<Recipe>>() {

        override fun doInBackground(vararg params: Void): List<Recipe> {
            return _asyncRecipeDao.all
        }
    }
}
