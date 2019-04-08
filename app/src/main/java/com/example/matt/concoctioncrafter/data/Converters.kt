/* Copyright Matthew Coughlin 2019 */

package com.example.matt.concoctioncrafter.data

import android.util.Log
import androidx.room.TypeConverter
import java.util.*

class Converters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fermentablesFromString(fermentableString: String): List<Fermentable> {
            Log.d("Testing", "Pulling fermentables from a string: $fermentableString")
            val fermentableList = ArrayList<Fermentable>()
            val fermentables = fermentableString.split(";")
            Log.d("Testing", "Fermentables found: ${fermentables.size}")

            for (fermentable in fermentables) {
                Log.d("Testing", "Fermentable: $fermentable")
                val fields = fermentable.split(",")

                if (fields.size == 2) {
                    val newFermentable = Fermentable(fields[0], java.lang.Float.valueOf(fields[1]))
                    Log.d("Testing", "Adding fermentable: $newFermentable")
                    fermentableList.add(newFermentable)
                }
            }

            return fermentableList
        }

        @TypeConverter
        @JvmStatic
        fun stringFromFermentables(fermentables: List<Fermentable>): String {
            Log.d("Testing", "Building a string from fermentables: " + fermentables.size)
            val fermentableStringBuilder = StringBuilder()

            for (fermentable in fermentables) {
                Log.d("Testing", "Fermentable: $fermentable")
                fermentableStringBuilder.append("${fermentable.name},")
                fermentableStringBuilder.append("${fermentable.amount_lbs};")
            }

            Log.d("Testing", "Fermentable String Built: $fermentableStringBuilder")
            return fermentableStringBuilder.toString()
        }

        @TypeConverter
        @JvmStatic
        fun hopsFromString(hopString: String): List<Hop> {
            Log.d("Testing", "Getting hops from string: $hopString")
            val hopList = ArrayList<Hop>()
            val hops = hopString.split(";")
            Log.d("Testing", "Hops found: ${hops.size}")

            for (hop in hops) {
                Log.d("Testing", "Hop: $hop")
                val fields = hop.split(",")

                if (fields.size == 3) {
                    val newHop = Hop(fields[0], java.lang.Float.valueOf(fields[1]), Integer.valueOf(fields[2]))
                    Log.d("Testing", "Adding hop: $newHop")
                    hopList.add(newHop)
                }
            }

            return hopList
        }

        @TypeConverter
        @JvmStatic
        fun stringFromHops(hops: List<Hop>): String {
            Log.d("Testing", "Building string from hops: ${hops.size}")
            val hopStringBuilder = StringBuilder()

            for (hop in hops) {
                Log.d("Testing", "Hop: $hop")
                hopStringBuilder.append("${hop.name},")
                hopStringBuilder.append("${hop.amount_oz},")
                hopStringBuilder.append("${hop.additionTime_min};")
            }

            Log.d("Testing", "Hop String Built: $hopStringBuilder")
            return hopStringBuilder.toString()
        }
    }
}
