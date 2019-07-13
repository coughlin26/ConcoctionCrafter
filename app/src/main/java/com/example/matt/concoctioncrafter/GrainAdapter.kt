/* Copyright Matt Coughlin 2019 */

package com.example.matt.concoctioncrafter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.example.matt.concoctioncrafter.data.Fermentable

class GrainAdapter internal constructor(private val grains: List<Fermentable>, private val context: Context?) : RecyclerView.Adapter<GrainAdapter.GrainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrainViewHolder {
        return GrainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.grain_row, parent, false) as ViewGroup)
    }

    override fun onBindViewHolder(holder: GrainViewHolder, position: Int) {
        val grainName = grains[position].name
        var selectedPosition = 0

        for (i in context!!.resources.getStringArray(R.array.grain_options).indices) {
            val grain = context.resources.getStringArray(R.array.grain_options)[i]
            if (grainName == grain) {
                selectedPosition = i
                break
            }
        }

        holder.spinner.setSelection(selectedPosition)
        holder.amount.text.clear()
        holder.amount.text.insert(0, grains[position].amount_lbs.toString())
    }

    override fun getItemCount() = grains.size

    inner class GrainViewHolder(private val viewGroup: ViewGroup) : RecyclerView.ViewHolder(viewGroup) {
        val spinner: Spinner = viewGroup.findViewById(R.id.spinner)
        val amount: EditText = viewGroup.findViewById(R.id.amount)
    }
}