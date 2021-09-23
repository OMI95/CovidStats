package com.omer.covidstats.view.dashboard.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.omer.covidstats.R
import com.omer.covidstats.model.Countries
import com.omer.covidstats.utils.HelperSnippets

class CountryListAdapter(private val data: List<Countries>): RecyclerView.Adapter<CountryListAdapter.ViewHolder>() {

    private var preExpandedPosition: Int = -1
    private var expandedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryListAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.country_layout, parent, false) as View
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryListAdapter.ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int  = data.size

    inner class ViewHolder(val v: View): RecyclerView.ViewHolder(v) {
        private val tvCName: TextView = v.findViewById(R.id.countryName)
        private val tvTCases: TextView = v.findViewById(R.id.countryTC)
        private val tvTCCases: TextView = v.findViewById(R.id.countryConfirmed)
        private val tvTDeaths: TextView = v.findViewById(R.id.countryDeaths)
        private val tvTRecovered: TextView = v.findViewById(R.id.countryRecovered)
        private val tvNCases: TextView = v.findViewById(R.id.confirmedNew)
        private val tvNDeaths: TextView = v.findViewById(R.id.deathsNew)
        private val tvNRecovered: TextView = v.findViewById(R.id.recoveredNew)
        private val subView: LinearLayout = v.findViewById(R.id.sub_view)

        fun bind(item: Countries) {
            val isExpanded = expandedPosition == adapterPosition
            if (isExpanded) {
                preExpandedPosition = expandedPosition
                subView.visibility = View.VISIBLE
                tvTCases.visibility = View.GONE
            }
            else {
                subView.visibility = View.GONE
                tvTCases.visibility = View.VISIBLE
            }
            tvCName.text = item.country
            tvTCases.text = HelperSnippets.getInstance().formatStringWithCommas(item.totalConfirmed)
            tvTCCases.text = HelperSnippets.getInstance().formatStringWithCommas(item.totalConfirmed)
            tvTDeaths.text = HelperSnippets.getInstance().formatStringWithCommas(item.totalDeaths)
            tvTRecovered.text = HelperSnippets.getInstance().formatStringWithCommas(item.totalRecovered)
            tvNCases.text = HelperSnippets.getInstance().formatStringWithCommas(item.newConfirmed)
            tvNDeaths.text = HelperSnippets.getInstance().formatStringWithCommas(item.newDeaths)
            tvNRecovered.text = HelperSnippets.getInstance().formatStringWithCommas(item.newRecovered)

            v.setOnClickListener {
                if (isExpanded) {
                    expandedPosition = -1
                } else {
                    expandedPosition = adapterPosition
                }
                notifyItemChanged(preExpandedPosition)
                notifyItemChanged(expandedPosition)
            }
        }
    }
}