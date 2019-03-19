/* Copyright Matthew Coughlin 2019 */

package com.example.matt.concoctioncrafter.data

import android.os.Parcel
import android.os.Parcelable

class Hop : Parcelable {
    var name: String? = null
    var amount: Float = 0.toFloat()
    var additionTime_min: Int = 0

    constructor(name: String, amount_oz: Float, additionTime_min: Int) {
        this.name = name
        amount = amount_oz
        this.additionTime_min = additionTime_min
    }

    constructor(source: Parcel) {
        name = source.readString()
        amount = source.readFloat()
        additionTime_min = source.readInt()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeFloat(amount)
        dest.writeInt(additionTime_min)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {

        @JvmField
        var CREATOR: Parcelable.Creator<Hop> = object : Parcelable.Creator<Hop> {
            override fun createFromParcel(source: Parcel): Hop {
                return Hop(source)
            }

            override fun newArray(size: Int): Array<Hop?> {
                return arrayOfNulls(size)
            }
        }
    }
}
