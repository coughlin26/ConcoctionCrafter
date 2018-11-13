/* Copyright Matthew Coughlin 2018 */

package com.example.matt.concoctioncrafter.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface RecipeDAO {
    @Query("SELECT * FROM `recipes.db`")
    List<Recipe> getAll();

    @Query("SELECT * FROM `recipes.db` ORDER BY recipe_name ASC")
    LiveData<List<Recipe>> getAllRecipes();

    @Query("SELECT * FROM `recipes.db` WHERE id IN (:recipeIds)")
    List<Recipe> loadAllByIds(int[] recipeIds);

    @Query("SELECT * FROM `recipes.db` WHERE id = :id LIMIT 1")
    Recipe findById(int id);

    @Query("SELECT * FROM `recipes.db` WHERE recipe_name = :name LIMIT 1")
    Recipe findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Recipe recipe);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Recipe... recipes);

    @Update
    void update(Recipe recipe);

    @Query("DELETE FROM `recipes.db`")
    void deleteAll();

    @Delete
    void delete(Recipe recipe);
}
