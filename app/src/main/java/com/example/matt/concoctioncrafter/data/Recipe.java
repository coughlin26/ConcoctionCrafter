/* Copyright Matthew Coughlin 2018 */

package com.example.matt.concoctioncrafter.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Recipe {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "recipe_name")
    private String recipeName;

    @ColumnInfo(name = "grain_1")
    private String grain_1;

    @ColumnInfo(name = "grain_1_amount")
    private float grain_1_amount;

    @ColumnInfo(name = "grain_2")
    private String grain_2;

    @ColumnInfo(name = "grain_2_amount")
    private float grain_2_amount;

    @ColumnInfo(name = "grain_3")
    private String grain_3;

    @ColumnInfo(name = "grain_3_amount")
    private float grain_3_amount;

    @ColumnInfo(name = "grain_4")
    private String grain_4;

    @ColumnInfo(name = "grain_4_amount")
    private float grain_4_amount;

    @ColumnInfo(name = "hop_1")
    private String hop_1;

    @ColumnInfo(name = "hop_1_amount")
    private float hop_1_amount;

    @ColumnInfo(name = "hop_2")
    private String hop_2;

    @ColumnInfo(name = "hop_2_amount")
    private float hop_2_amount;

    @ColumnInfo(name = "hop_3")

    private String hop_3;

    @ColumnInfo(name = "hop_3_amount")
    private float hop_3_amount;

    @ColumnInfo(name = "hop_4")
    private String hop_4;

    @ColumnInfo(name = "hop_4_amount")
    private float hop_4_amount;

    @ColumnInfo(name = "yeast")
    private String yeast;

    public Recipe(@NonNull String recipeName,
                  @NonNull String grain_1,
                  float grain_1_amount,
                  @NonNull String grain_2,
                  float grain_2_amount,
                  @NonNull String grain_3,
                  float grain_3_amount,
                  @NonNull String grain_4,
                  float grain_4_amount,
                  @NonNull String hop_1,
                  float hop_1_amount,
                  @NonNull String hop_2,
                  float hop_2_amount,
                  @NonNull String hop_3,
                  float hop_3_amount,
                  @NonNull String hop_4,
                  float hop_4_amount,
                  @NonNull String yeast) {
        this.recipeName = recipeName;
        this.grain_1 = grain_1;
        this.grain_1_amount = grain_1_amount;
        this.grain_2 = grain_2;
        this.grain_2_amount = grain_2_amount;
        this.grain_3 = grain_3;
        this.grain_3_amount = grain_3_amount;
        this.grain_4 = grain_4;
        this.grain_4_amount = grain_4_amount;
        this.hop_1 = hop_1;
        this.hop_1_amount = hop_1_amount;
        this.hop_2 = hop_2;
        this.hop_2_amount = hop_2_amount;
        this.hop_3 = hop_3;
        this.hop_3_amount = hop_3_amount;
        this.hop_4 = hop_4;
        this.hop_4_amount = hop_4_amount;
        this.yeast = yeast;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getGrain_1() {
        return grain_1;
    }

    public void setGrain_1(String grain_1) {
        this.grain_1 = grain_1;
    }

    public String getGrain_2() {
        return grain_2;
    }

    public void setGrain_2(String grain_2) {
        this.grain_2 = grain_2;
    }

    public String getGrain_3() {
        return grain_3;
    }

    public void setGrain_3(String grain_3) {
        this.grain_3 = grain_3;
    }

    public String getGrain_4() {
        return grain_4;
    }

    public void setGrain_4(String grain_4) {
        this.grain_4 = grain_4;
    }

    public float getGrain_1_amount() {
        return grain_1_amount;
    }

    public void setGrain_1_amount(float grain_1_amount) {
        this.grain_1_amount = grain_1_amount;
    }

    public float getGrain_2_amount() {
        return grain_2_amount;
    }

    public void setGrain_2_amount(float grain_2_amount) {
        this.grain_2_amount = grain_2_amount;
    }

    public float getGrain_3_amount() {
        return grain_3_amount;
    }

    public void setGrain_3_amount(float grain_3_amount) {
        this.grain_3_amount = grain_3_amount;
    }

    public float getGrain_4_amount() {
        return grain_4_amount;
    }

    public void setGrain_4_amount(float grain_4_amount) {
        this.grain_4_amount = grain_4_amount;
    }

    public String getHop_1() {
        return hop_1;
    }

    public void setHop_1(String hop_1) {
        this.hop_1 = hop_1;
    }

    public String getHop_2() {
        return hop_2;
    }

    public void setHop_2(String hop_2) {
        this.hop_2 = hop_2;
    }

    public String getHop_3() {
        return hop_3;
    }

    public void setHop_3(String hop_3) {
        this.hop_3 = hop_3;
    }

    public String getHop_4() {
        return hop_4;
    }

    public void setHop_4(String hop_4) {
        this.hop_4 = hop_4;
    }

    public float getHop_1_amount() {
        return hop_1_amount;
    }

    public void setHop_1_amount(float hop_1_amount) {
        this.hop_1_amount = hop_1_amount;
    }

    public float getHop_2_amount() {
        return hop_2_amount;
    }

    public void setHop_2_amount(float hop_2_amount) {
        this.hop_2_amount = hop_2_amount;
    }

    public float getHop_3_amount() {
        return hop_3_amount;
    }

    public void setHop_3_amount(float hop_3_amount) {
        this.hop_3_amount = hop_3_amount;
    }

    public float getHop_4_amount() {
        return hop_4_amount;
    }

    public void setHop_4_amount(float hop_4_amount) {
        this.hop_4_amount = hop_4_amount;
    }

    public String getYeast() {
        return yeast;
    }

    public void setYeast(String yeast) {
        this.yeast = yeast;
    }
}
