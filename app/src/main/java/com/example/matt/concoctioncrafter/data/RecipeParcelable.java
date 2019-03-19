/* Copyright Matthew Coughlin 2018, 2019 */

package com.example.matt.concoctioncrafter.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

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
        final List<Fermentable> fermentables = new ArrayList<>();
        final List<Hop> hops = new ArrayList<>();
        final String name = in.readString();
        in.readTypedList(fermentables, Fermentable.CREATOR);
        in.readTypedList(hops, Hop.CREATOR);
        final String yeast = in.readString();

        _recipe = new Recipe(name == null ? "Unnamed Beer" : name, "No Style", fermentables, hops, yeast == null ? "Other" : yeast);
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
        dest.writeTypedList(_recipe.get_fermentables());
        dest.writeTypedList(_recipe.get_hops());
        dest.writeString(_recipe.get_yeast());
    }
}
