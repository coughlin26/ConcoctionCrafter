/* Copyright Matthew Coughlin 2018, 2019, 2020 */

package dev.mattcoughlin.concoctioncrafter.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecipeParcelable(val recipe: Recipe) : Parcelable
