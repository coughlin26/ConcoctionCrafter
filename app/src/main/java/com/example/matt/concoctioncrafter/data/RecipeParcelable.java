/* Copyright Matthew Coughlin 2018 */

package com.example.matt.concoctioncrafter.data;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeParcelable implements Parcelable {
    private Recipe _recipe;

    public static Creator CREATOR = new Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            return new RecipeParcelable(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new RecipeParcelable[size];
        }
    };

    private RecipeParcelable(final Parcel in) {
        _recipe = new Recipe(in.readString(),
                in.readString(),
                in.readFloat(),
                in.readString(),
                in.readFloat(),
                in.readString(),
                in.readFloat(),
                in.readString(),
                in.readFloat(),
                in.readString(),
                in.readFloat(),
                in.readString(),
                in.readFloat(),
                in.readString(),
                in.readFloat(),
                in.readString(),
                in.readFloat(),
                in.readString());
    }

    public RecipeParcelable(final Recipe recipe) {
        _recipe = recipe;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_recipe.getRecipeName());
        dest.writeString(_recipe.getGrain_1());
        dest.writeFloat(_recipe.getGrain_1_amount());
        dest.writeString(_recipe.getGrain_2());
        dest.writeFloat(_recipe.getGrain_2_amount());
        dest.writeString(_recipe.getGrain_3());
        dest.writeFloat(_recipe.getGrain_3_amount());
        dest.writeString(_recipe.getGrain_4());
        dest.writeFloat(_recipe.getGrain_4_amount());
        dest.writeString(_recipe.getHop_1());
        dest.writeFloat(_recipe.getHop_1_amount());
        dest.writeString(_recipe.getHop_2());
        dest.writeFloat(_recipe.getHop_2_amount());
        dest.writeString(_recipe.getHop_3());
        dest.writeFloat(_recipe.getHop_3_amount());
        dest.writeString(_recipe.getHop_4());
        dest.writeFloat(_recipe.getHop_4_amount());
        dest.writeString(_recipe.getYeast());
    }
}
