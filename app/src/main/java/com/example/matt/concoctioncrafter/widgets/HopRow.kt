/* Copyright Matthew Coughlin 2019 */

package com.example.matt.concoctioncrafter.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.matt.concoctioncrafter.R

class HopRow(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs, R.layout.hop_row) {
    fun getName(): String {
        return this.findViewById<Spinner>(R.id.spinner).selectedItem.toString()
    }

    fun getAmount(): Float {
        return this.findViewById<EditText>(R.id.amount).text.toString().toFloat()
    }

    fun getTime(): Int {
        return this.findViewById<EditText>(R.id.time).text.toString().toInt()
    }
}