/* Copyright Matthew Coughlin 2018, 2019 */

package com.example.matt.concoctioncrafter.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "recipes.db")
@Parcelize
data class Recipe(@PrimaryKey var recipeName: String,
                  var style: String,
                  var fermentables: List<Fermentable>,
                  var hops: List<Hop>,
                  var yeast: String) : Parcelable {
    constructor() : this("", "", ArrayList(), ArrayList(), "")
}
