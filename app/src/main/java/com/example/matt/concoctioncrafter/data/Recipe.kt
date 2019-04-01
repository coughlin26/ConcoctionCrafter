/* Copyright Matthew Coughlin 2018, 2019 */

package com.example.matt.concoctioncrafter.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes.db")
class Recipe(@field:ColumnInfo(name = "recipe_name")
             var _recipeName: String,
             var _style: String,
             var _fermentables: List<Fermentable>,
             var _hops: List<Hop>,
             var _yeast: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
