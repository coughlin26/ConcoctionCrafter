/* Copyright Matthew Coughlin 2019, 2020 */

package dev.mattcoughlin.concoctioncrafter.data

import android.util.Log
import androidx.room.TypeConverter
import java.util.*

class Converters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fermentablesFromString(fermentableString: String): List<Fermentable> {
            Log.v("Testing", "Pulling fermentables from a string: $fermentableString")
            val fermentableList = ArrayList<Fermentable>()
            val fermentables = fermentableString.split(";")
            Log.v("Testing", "Fermentables found: ${fermentables.size}")

            for (fermentable in fermentables) {
                Log.v("Testing", "Fermentable: $fermentable")
                val fields = fermentable.split(",")

                if (fields.size == 2) {
                    val newFermentable = Fermentable(fields[0], java.lang.Float.valueOf(fields[1]))
                    Log.v("Testing", "Adding fermentable: $newFermentable")
                    fermentableList.add(newFermentable)
                }
            }

            return fermentableList
        }

        @TypeConverter
        @JvmStatic
        fun stringFromFermentables(fermentables: List<Fermentable>): String {
            Log.v("Testing", "Building a string from fermentables: " + fermentables.size)
            val fermentableStringBuilder = StringBuilder()

            for (fermentable in fermentables) {
                Log.v("Testing", "Fermentable: $fermentable")
                fermentableStringBuilder.append("${fermentable.name},")
                fermentableStringBuilder.append("${fermentable.amount_lbs};")
            }

            Log.v("Testing", "Fermentable String Built: $fermentableStringBuilder")
            return fermentableStringBuilder.toString()
        }

        @TypeConverter
        @JvmStatic
        fun hopsFromString(hopString: String): List<Hop> {
            Log.v("Testing", "Getting hops from string: $hopString")
            val hopList = ArrayList<Hop>()
            val hops = hopString.split(";")
            Log.v("Testing", "Hops found: ${hops.size}")

            for (hop in hops) {
                Log.v("Testing", "Hop: $hop")
                val fields = hop.split(",")

                if (fields.size == 3) {
                    val newHop = Hop(fields[0], java.lang.Float.valueOf(fields[1]), Integer.valueOf(fields[2]))
                    Log.v("Testing", "Adding hop: $newHop")
                    hopList.add(newHop)
                }
            }

            return hopList
        }

        @TypeConverter
        @JvmStatic
        fun stringFromHops(hops: List<Hop>): String {
            Log.v("Testing", "Building string from hops: ${hops.size}")
            val hopStringBuilder = StringBuilder()

            for (hop in hops) {
                Log.v("Testing", "Hop: $hop")
                hopStringBuilder.append("${hop.name},")
                hopStringBuilder.append("${hop.amount_oz},")
                hopStringBuilder.append("${hop.additionTime_min};")
            }

            Log.v("Testing", "Hop String Built: $hopStringBuilder")
            return hopStringBuilder.toString()
        }
    }
}
