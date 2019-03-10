/* Copyright Matthew Coughlin 2018, 2019 */

package com.example.matt.concoctioncrafter.data;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipes.db")
public class Recipe {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "recipe_name")
    private String _recipeName;

    private List<Fermentable> _fermentables;

    private List<Hop> _hops;

    private String _yeast;

    public Recipe(@NonNull String recipeName,
                  @NonNull List<Fermentable> fermentables,
                  @NonNull List<Hop> hops,
                  @NonNull String yeast) {
        _recipeName = recipeName;
        _fermentables = fermentables;
        _hops = hops;
        _yeast = yeast;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String get_recipeName() {
        return _recipeName;
    }

    public void set_recipeName(final String recipeName) {
        _recipeName = recipeName;
    }

    public List<Fermentable> get_fermentables() {
        return _fermentables;
    }

    public void set_fermentables(List<Fermentable> fermentables) {
        _fermentables = fermentables;
    }

    public List<Hop> get_hops() {
        return _hops;
    }

    public void set_hops(List<Hop> hops) {
        _hops = hops;
    }

    public String get_yeast() {
        return _yeast;
    }

    public void set_yeast(String yeast) {
        _yeast = yeast;
    }
}
