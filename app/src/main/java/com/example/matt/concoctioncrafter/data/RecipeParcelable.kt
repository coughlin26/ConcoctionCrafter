/* Copyright Matthew Coughlin 2018, 2019 */

package com.example.matt.concoctioncrafter.data

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class RecipeParcelable : Parcelable {
    var recipe: Recipe? = null
        private set

    private constructor(`in`: Parcel) {
        val fermentables = ArrayList<Fermentable>()
        val hops = ArrayList<Hop>()
        val name = `in`.readString()
        `in`.readTypedList(fermentables, Fermentable.CREATOR)
        `in`.readTypedList(hops, Hop.CREATOR)
        val yeast = `in`.readString()

        recipe = Recipe(name ?: "Unnamed Beer", "No Style", fermentables, hops, yeast ?: "Other")
    }

    constructor(recipe: Recipe) {
        this.recipe = recipe
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(recipe!!._recipeName)
        dest.writeTypedList(recipe!!._fermentables)
        dest.writeTypedList(recipe!!._hops)
        dest.writeString(recipe!!._yeast)
    }

    companion object {

        @JvmField
        var CREATOR: Parcelable.Creator<RecipeParcelable> = object : Parcelable.Creator<RecipeParcelable> {
            override fun createFromParcel(source: Parcel): RecipeParcelable {
                return RecipeParcelable(source)
            }

            override fun newArray(size: Int): Array<RecipeParcelable?> {
                return arrayOfNulls(size)
            }
        }
    }
}
