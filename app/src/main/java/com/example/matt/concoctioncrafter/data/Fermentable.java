/* Copyright Matthew Coughlin 2019 */

package com.example.matt.concoctioncrafter.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Fermentable implements Parcelable {
    private String _name;
    private float _amount_lbs;

    public static Creator<Fermentable> CREATOR = new Creator<Fermentable>() {
        @Override
        public Fermentable createFromParcel(final Parcel source) {
            return new Fermentable(source);
        }

        @Override
        public Fermentable[] newArray(final int size) {
            return new Fermentable[size];
        }
    };

    public Fermentable(final Parcel in) {
        setName(in.readString());
        setAmount_lbs(in.readFloat());
    }

    public Fermentable(final String name, final float amount_lbs) {
        _name = name;
        _amount_lbs = amount_lbs;
    }

    public String getName() {
        return _name;
    }

    public void setName(final String name) {
        _name = name;
    }

    public float getAmount_lbs() {
        return _amount_lbs;
    }

    public void setAmount_lbs(final float amount_lbs) {
        _amount_lbs = amount_lbs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(getName());
        dest.writeFloat(getAmount_lbs());
    }
}
