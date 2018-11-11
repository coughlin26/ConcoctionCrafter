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
    @Query("SELECT * FROM recipe")
    List<Recipe> getAll();

    @Query("SELECT * FROM recipe ORDER BY recipe_name ASC")
    LiveData<List<Recipe>> getAllRecipes();

    @Query("SELECT * FROM recipe WHERE id IN (:recipeIds)")
    List<Recipe> loadAllByIds(int[] recipeIds);

    @Query("SELECT * FROM recipe WHERE id = :id LIMIT 1")
    Recipe findById(int id);

    @Query("SELECT * FROM recipe WHERE recipe_name LIKE :name LIMIT 1")
    Recipe findByName(String name);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    void insert(Recipe recipe);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    void insert(Recipe... recipes);

    @Update
    void update(Recipe recipe);

    @Query("DELETE FROM recipe")
    void deleteAll();

    @Delete
    void delete(Recipe recipe);
}
