/* Copyright Matthew Coughlin 2018, 2019, 2020 */

package dev.mattcoughlin.concoctioncrafter.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecipeDAO {
    @get:Query("SELECT * FROM `recipes.db`")
    val all: List<Recipe>

    @get:Query("SELECT * FROM `recipes.db` ORDER BY recipeName ASC")
    val allRecipes: LiveData<List<Recipe>>

    @Query("SELECT * FROM `recipes.db` WHERE recipeName = :name LIMIT 1")
    fun findByName(name: String): Recipe

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: Recipe)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg recipes: Recipe)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(recipe: Recipe)

    @Query("DELETE FROM `recipes.db`")
    fun deleteAll()

    @Delete
    fun delete(recipe: Recipe)
}
