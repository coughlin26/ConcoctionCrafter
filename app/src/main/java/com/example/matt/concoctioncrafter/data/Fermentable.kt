/* Copyright Matthew Coughlin 2019 */

package com.example.matt.concoctioncrafter.data

import android.os.Parcel
import android.os.Parcelable

class Fermentable : Parcelable {
    var name: String? = null
    var amount_lbs: Float = 0.toFloat()

    constructor(`in`: Parcel) {
        name = `in`.readString()
        amount_lbs = `in`.readFloat()
    }

    constructor(name: String, amount_lbs: Float) {
        this.name = name
        this.amount_lbs = amount_lbs
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeFloat(amount_lbs)
    }

    companion object {

        @JvmField
        var CREATOR: Parcelable.Creator<Fermentable> = object : Parcelable.Creator<Fermentable> {
            override fun createFromParcel(source: Parcel): Fermentable {
                return Fermentable(source)
            }

            override fun newArray(size: Int): Array<Fermentable?> {
                return arrayOfNulls(size)
            }
        }
    }
}
