/* Copyright Matthew Coughlin 2018, 2019 */

package com.example.matt.concoctioncrafter.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecipeDAO {
    @get:Query("SELECT * FROM `recipes.db`")
    val all: List<Recipe>

    @get:Query("SELECT * FROM `recipes.db` ORDER BY _recipeName ASC")
    val allRecipes: LiveData<List<Recipe>>

    @Query("SELECT * FROM `recipes.db` WHERE _recipeName = :name LIMIT 1")
    fun findByName(name: String): Recipe

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: Recipe)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg recipes: Recipe)

    @Update
    fun update(recipe: Recipe)

    @Query("DELETE FROM `recipes.db`")
    fun deleteAll()

    @Delete
    fun delete(recipe: Recipe)
}
