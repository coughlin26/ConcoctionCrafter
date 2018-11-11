/* Copyright Matthew Coughlin 2018 */

package com.example.matt.concoctioncrafter.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Recipe.class}, version = 1)
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
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    new PopulateDbAsync(INSTANCE).execute();
                                }

                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                    new PopulateDbAsync(INSTANCE).execute();
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public void clearDb() {
        if (INSTANCE != null) {
            new PopulateDbAsync(INSTANCE).execute();
        }
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final RecipeDAO recipeDAO;

        public PopulateDbAsync(RecipeDatabase instance) {
            recipeDAO = instance.recipeDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            recipeDAO.deleteAll();

            final Recipe recipe = new Recipe("Sudz",
                    "2 Row",
                    2f,
                    "None",
                    0f,
                    "None",
                    0f,
                    "None",
                    0f,
                    "Cascade",
                    4f,
                    "None",
                    0,
                    "None",
                    0,
                    "None",
                    0,
                    "American Ale");
            recipeDAO.insert(recipe);
            return null;
        }
    }
}
