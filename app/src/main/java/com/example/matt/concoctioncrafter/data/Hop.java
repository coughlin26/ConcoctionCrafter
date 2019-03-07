/* Copyright Matthew Coughlin 2019 */

package com.example.matt.concoctioncrafter.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Hop implements Parcelable {
    private String _name;
    private float _amount_oz;
    private int _additionTime_min;

    public static Creator<Hop> CREATOR = new Creator<Hop>() {
        @Override
        public Hop createFromParcel(Parcel source) {
            return new Hop(source);
        }

        @Override
        public Hop[] newArray(int size) {
            return new Hop[size];
        }
    };

    public Hop(final String name, final float amount_oz, final int additionTime_min) {
        _name = name;
        _amount_oz = amount_oz;
        _additionTime_min = additionTime_min;
    }

    public Hop(final Parcel source) {
        setName(source.readString());
        setAmount(source.readFloat());
        setAdditionTime_min(source.readInt());
    }

    public String getName() {
        return _name;
    }

    public void setName(final String _name) {
        this._name = _name;
    }

    public float getAmount() {
        return _amount_oz;
    }

    public void setAmount(final float _amount) {
        this._amount_oz = _amount;
    }

    public int getAdditionTime_min() {
        return _additionTime_min;
    }

    public void setAdditionTime_min(final int _additionTime_min) {
        this._additionTime_min = _additionTime_min;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(getName());
        dest.writeFloat(getAmount());
        dest.writeInt(getAdditionTime_min());
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
