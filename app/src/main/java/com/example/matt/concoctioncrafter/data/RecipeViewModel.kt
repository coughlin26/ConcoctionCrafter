/* Copyright Matthew Coughlin 2018, 2019 */

package com.example.matt.concoctioncrafter.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    private val _recipeRepository: RecipeRepository
    val recipeList: LiveData<List<Recipe>>

    val all: List<Recipe>?
        get() {
            Log.d(TAG, "Getting all recipes, unordered")
            return _recipeRepository.all
        }

    init {
        _recipeRepository = RecipeRepository(application)
        recipeList = _recipeRepository.allRecipes
    }

    fun insert(recipe: Recipe) {
        Log.d(TAG, "Inserting recipe: " + recipe._recipeName)
        _recipeRepository.insert(recipe)
    }

    fun insert(vararg recipes: Recipe) {
        Log.d(TAG, "Inserting recipes, starting with: " + recipes[0]._recipeName)
        _recipeRepository.insert(*recipes)
    }

    fun update(recipe: Recipe) {
        Log.d(TAG, "Updating recipe named: " + recipe._recipeName)
        _recipeRepository.update(recipe)
    }

    fun update(vararg recipes: Recipe) {
        Log.d(TAG, "Updating recipes, stating with: " + recipes[0]._recipeName)
        _recipeRepository.update(*recipes)
    }

    fun findByName(name: String): Recipe? {
        Log.d(TAG, "Searching for $name")
        return _recipeRepository.findByName(name)
    }

    fun delete(recipe: Recipe) {
        Log.d(TAG, "Deleting recipe named: " + recipe._recipeName)
        _recipeRepository.delete(recipe)
    }

    fun deleteAll() {
        Log.d(TAG, "Deleting all recipes...")
        _recipeRepository.deleteAll()
    }

    companion object {
        private val TAG = "Recipe_View_Model"
    }
}
