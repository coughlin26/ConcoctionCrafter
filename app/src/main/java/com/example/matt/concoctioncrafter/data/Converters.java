/* Copyright Matthew Coughlin 2019 */

package com.example.matt.concoctioncrafter.data;

import java.util.ArrayList;
import java.util.List;

import androidx.room.TypeConverter;

public class Converters {
    @TypeConverter
    public static List<Fermentable> fermtablesFromString(final String fermentableString) {
        final List<Fermentable> fermentableList = new ArrayList<>();
        final String[] fermentables = fermentableString.split(";");

        for (final String fermentable : fermentables) {
            final String[] fields = fermentable.split(",");
            final Fermentable newFermentable = new Fermentable(fields[0], Float.valueOf(fields[1]));
            fermentableList.add(newFermentable);
        }

        return fermentableList;
    }

    @TypeConverter
    public static String stringFromFermtables(final List<Fermentable> fermentables) {
        StringBuilder fermentableStringBuilder = new StringBuilder();

        for (final Fermentable fermentable : fermentables) {
            fermentableStringBuilder.append(fermentable.getName());
            fermentableStringBuilder.append(",");
            fermentableStringBuilder.append(fermentable.getAmount_lbs());
            fermentableStringBuilder.append(";");
        }

        return fermentableStringBuilder.toString();
    }

    @TypeConverter
    public static List<Hop> hopsFromString(final String hopString) {
        final List<Hop> hopList = new ArrayList<>();
        final String[] hops = hopString.split(";");

        for (final String hop : hops) {
            final String[] fields = hop.split(",");
            final Hop newHop = new Hop(fields[0], Float.valueOf(fields[1]), Integer.valueOf(fields[2]));
            hopList.add(newHop);
        }

        return hopList;
    }

    @TypeConverter
    public static String stringFromHops(final List<Hop> hops) {
        StringBuilder hopStringBuilder = new StringBuilder();

        for (final Hop hop : hops) {
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
