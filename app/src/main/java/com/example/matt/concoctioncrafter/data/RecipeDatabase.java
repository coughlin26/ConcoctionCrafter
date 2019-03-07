/* Copyright Matthew Coughlin 2018, 2019 */

package com.example.matt.concoctioncrafter.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Recipe.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class RecipeDatabase extends RoomDatabase {
    private static final String DB_NAME = "recipes.db";
    private static RecipeDatabase INSTANCE;

    public abstract RecipeDAO recipeDAO();

    public static RecipeDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RecipeDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RecipeDatabase.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
