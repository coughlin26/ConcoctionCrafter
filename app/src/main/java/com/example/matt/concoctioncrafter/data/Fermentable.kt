/* Copyright Matthew Coughlin 2019 */

package com.example.matt.concoctioncrafter.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Fermentable(var name: String, var amount_lbs: Float) : Parcelable
