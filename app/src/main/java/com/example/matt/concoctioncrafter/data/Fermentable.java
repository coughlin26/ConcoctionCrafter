/* Copyright Matthew Coughlin 2019 */

package com.example.matt.concoctioncrafter.data;

public class Fermentable {
    private String _name;
    private float _amount_lbs;

    public Fermentable(final String name, final float amount_lbs) {
        _name = name;
        _amount_lbs = amount_lbs;
    }

    public String getName() {
        return _name;
    }

    public void setName(final String name) {
        _name = name;
    }

    public float getAmount_lbs() {
        return _amount_lbs;
    }

    public void setAmount_lbs(final float amount_lbs) {
        _amount_lbs = amount_lbs;
    }
}
