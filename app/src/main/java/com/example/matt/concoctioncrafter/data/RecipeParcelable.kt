/* Copyright Matthew Coughlin 2018, 2019 */

package com.example.matt.concoctioncrafter.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecipeParcelable(val recipe: Recipe) : Parcelable
