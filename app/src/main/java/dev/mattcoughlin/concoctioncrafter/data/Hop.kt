/* Copyright Matthew Coughlin 2019, 2020 */

package dev.mattcoughlin.concoctioncrafter.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hop(var name: String, var amount_oz: Float, var additionTime_min: Int) : Parcelable
