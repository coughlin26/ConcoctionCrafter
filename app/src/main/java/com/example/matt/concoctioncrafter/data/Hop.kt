/* Copyright Matthew Coughlin 2019 */

package com.example.matt.concoctioncrafter.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hop(var name: String, var amount_oz: Float, var additionTime_min: Int) : Parcelable
