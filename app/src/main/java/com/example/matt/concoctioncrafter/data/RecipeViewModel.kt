/* Copyright Matthew Coughlin 2018, 2019 */

package com.example.matt.concoctioncrafter.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    private val _recipeRepository: RecipeRepository = RecipeRepository(application)
    val recipeList: LiveData<List<Recipe>>
    var recipe: Recipe? = null

    val all: List<Recipe>?
        get() {
            Log.d(TAG, "Getting all recipes, unordered")
            return _recipeRepository.all
        }

    init {
        recipeList = _recipeRepository.allRecipes
    }

    fun insert(recipe: Recipe) {
        Log.d(TAG, "Inserting recipe: $recipe")
        _recipeRepository.insert(recipe)
    }

    fun insert(vararg recipes: Recipe) {
        for (recipe in recipes) {
            Log.d(TAG, "Inserting recipes, starting with: $recipe")
        }
        _recipeRepository.insert(*recipes)
    }

    fun update(recipe: Recipe) {
        Log.d(TAG, "Updating recipe named: $recipe")
        _recipeRepository.update(recipe)
    }

    fun update(vararg recipes: Recipe) {
        for (recipe in recipes) {
            Log.d(TAG, "Updating recipes, stating with: $recipe")
        }
        _recipeRepository.update(*recipes)
    }

    fun findByName(name: String): Recipe? {
        Log.d(TAG, "Searching for $name")
        recipe = _recipeRepository.findByName(name)
        Log.d("TESTING", "Retrieved: $recipe")
        return recipe
    }

    fun delete(recipe: Recipe) {
        Log.d(TAG, "Deleting recipe: $recipe")
        _recipeRepository.delete(recipe)
    }

    fun deleteAll() {
        Log.d(TAG, "Deleting all recipes...")
        _recipeRepository.deleteAll()
    }

    companion object {
        private const val TAG = "Testing"
    }
}
