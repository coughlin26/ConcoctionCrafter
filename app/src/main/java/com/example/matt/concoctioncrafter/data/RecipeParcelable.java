/* Copyright Matthew Coughlin 2018, 2019 */

package com.example.matt.concoctioncrafter.data;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeParcelable implements Parcelable {
    private Recipe _recipe;

    public static Creator<RecipeParcelable> CREATOR = new Creator<RecipeParcelable>() {
        @Override
        public RecipeParcelable createFromParcel(Parcel source) {
            return new RecipeParcelable(source);
        }

        @Override
        public RecipeParcelable[] newArray(int size) {
            return new RecipeParcelable[size];
        }
    };

    private RecipeParcelable(final Parcel in) {
        _recipe = new Recipe(in.readString(),
                in.readArrayList(ClassLoader.getSystemClassLoader()),
                in.readArrayList(ClassLoader.getSystemClassLoader()),
                in.readString());
    }

    public RecipeParcelable(final Recipe recipe) {
        _recipe = recipe;
    }

    public Recipe getRecipe() {
        return _recipe;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_recipe.get_recipeName());
        dest.writeList(_recipe.get_fermentables());
        dest.writeList(_recipe.get_hops());
        dest.writeString(_recipe.get_yeast());
    }
}
