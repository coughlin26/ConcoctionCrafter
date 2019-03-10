/* Copyright Matthew Coughlin 2019 */

package com.example.matt.concoctioncrafter.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.room.TypeConverter;

public class Converters {
    @TypeConverter
    public static List<Fermentable> fermentablesFromString(final String fermentableString) {
        Log.d("Testing", "Pulling fermentables from a string: " + fermentableString);
        final List<Fermentable> fermentableList = new ArrayList<>();
        final String[] fermentables = fermentableString.split(";");

        for (final String fermentable : fermentables) {
            Log.d("Testing", "Fermentable: " + fermentable);
            final String[] fields = fermentable.split(",");

            if (fields.length == 2) {
                final Fermentable newFermentable = new Fermentable(fields[0], Float.valueOf(fields[1]));
                Log.d("Testing", "Adding fermentable: " + newFermentable.getName());
                fermentableList.add(newFermentable);
            }
        }

        return fermentableList;
    }

    @TypeConverter
    public static String stringFromFermentables(final List<Fermentable> fermentables) {
        Log.d("Testing", "Building a string from fermentables: " + fermentables.size());
        StringBuilder fermentableStringBuilder = new StringBuilder();

        for (final Fermentable fermentable : fermentables) {
            Log.d("Testing", "Fermentable: " + fermentable.getName());
            fermentableStringBuilder.append(fermentable.getName());
            fermentableStringBuilder.append(",");
            fermentableStringBuilder.append(fermentable.getAmount_lbs());
            fermentableStringBuilder.append(";");
        }

        return fermentableStringBuilder.toString();
    }

    @TypeConverter
    public static List<Hop> hopsFromString(final String hopString) {
        Log.d("Testing", "Getting hops from string: " + hopString);
        final List<Hop> hopList = new ArrayList<>();
        final String[] hops = hopString.split(";");

        for (final String hop : hops) {
            Log.d("Testing", "Hop: " + hop);
            final String[] fields = hop.split(",");

            if (fields.length == 3) {
                final Hop newHop = new Hop(fields[0], Float.valueOf(fields[1]), Integer.valueOf(fields[2]));
                Log.d("Testing", "Adding hop: " + newHop.getName());
                hopList.add(newHop);
            }
        }

        return hopList;
    }

    @TypeConverter
    public static String stringFromHops(final List<Hop> hops) {
        Log.d("Testing", "Building string from hops: " + hops.size());
        StringBuilder hopStringBuilder = new StringBuilder();

        for (final Hop hop : hops) {
            Log.d("Testing", "Hop: " + hop.getName());
            hopStringBuilder.append(hop.getName());
            hopStringBuilder.append(",");
            hopStringBuilder.append(hop.getAmount());
            hopStringBuilder.append(",");
            hopStringBuilder.append(hop.getAdditionTime_min());
            hopStringBuilder.append(";");
        }

        return hopStringBuilder.toString();
    }
}
