/* Copyright Matt Coughlin 2019 */

package com.example.matt.concoctioncrafter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.example.matt.concoctioncrafter.data.Hop

class HopAdapter internal constructor(private val hops: List<Hop>, private val context: Context?) : RecyclerView.Adapter<HopAdapter.HopViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HopViewHolder {
        return HopViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.grain_row, parent, false) as ViewGroup)
    }

    override fun onBindViewHolder(holder: HopViewHolder, position: Int) {
        val grainName = hops[position].name
        var selectedPosition = 0

        for (i in context!!.resources.getStringArray(R.array.grain_options).indices) {
            val grain = context.resources.getStringArray(R.array.hop_options)[i]
            if (grainName == grain) {
                selectedPosition = i
                break
            }
        }

        holder.spinner.setSelection(selectedPosition)
        holder.amount.text.clear()
        holder.amount.text.insert(0, hops[position].amount_oz.toString())
    }

    override fun getItemCount() = hops.size

    inner class HopViewHolder(private val viewGroup: ViewGroup) : RecyclerView.ViewHolder(viewGroup) {
        val spinner: Spinner = viewGroup.findViewById(R.id.spinner)
        val amount: EditText = viewGroup.findViewById(R.id.amount)
        val time: EditText = viewGroup.findViewById(R.id.time)
    }
}