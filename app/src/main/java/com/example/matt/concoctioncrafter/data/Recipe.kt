/* Copyright Matthew Coughlin 2018, 2019 */

package com.example.matt.concoctioncrafter.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "recipes.db")
@Parcelize
data class Recipe(@PrimaryKey var _recipeName: String,
                  var _style: String,
                  var _fermentables: List<Fermentable>,
                  var _hops: List<Hop>,
                  var _yeast: String) : Parcelable
