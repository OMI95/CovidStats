package com.omer.covidstats.view.dashboard

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.omer.covidstats.R
import com.omer.covidstats.model.Global
import com.omer.covidstats.utils.HelperSnippets
import com.omer.covidstats.viewmodel.CovidViewModel


class DashboardFragment : Fragment() {

    private lateinit var tvTotalCases: TextView
    private lateinit var tvTotalDeaths: TextView
    private lateinit var tvTotalRecovered: TextView
    private lateinit var tvNewCases: TextView
    private lateinit var tvNewDeaths: TextView
    private lateinit var tvNewRecovered: TextView
    private lateinit var lastSync: TextView

    private val covidVM: CovidViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        tvTotalCases = view.findViewById<TextView>(R.id.casesTotal)
        tvTotalDeaths = view.findViewById<TextView>(R.id.totalDeaths)
        tvTotalRecovered = view.findViewById<TextView>(R.id.totalRecovered)
        tvNewCases = view.findViewById<TextView>(R.id.newCases)
        tvNewDeaths = view.findViewById<TextView>(R.id.newDeaths)
        tvNewRecovered = view.findViewById<TextView>(R.id.newRecovered)
        lastSync = view.findViewById<TextView>(R.id.lastSync)
        return view
    }

    private fun setupObservers() {
        covidVM.summaryLiveData.observe(this, Observer {
            updateUI(it.global)
        })
    }

    private fun updateUI(global: Global) {
        Log.i("Log: Global", global.newConfirmed.toString())
        tvTotalCases.text = HelperSnippets.getInstance().formatStringWithCommas(global.totalConfirmed)
        tvTotalDeaths.text = HelperSnippets.getInstance().formatStringWithCommas(global.totalDeaths)
        tvTotalRecovered.text = HelperSnippets.getInstance().formatStringWithCommas(global.totalRecovered)
        tvNewCases.text = HelperSnippets.getInstance().formatStringWithCommas(global.newConfirmed)
        tvNewRecovered.text = HelperSnippets.getInstance().formatStringWithCommas(global.newRecovered)
        tvNewDeaths.text = HelperSnippets.getInstance().formatStringWithCommas(global.newDeaths)

        lastSync.text = "Last Sync: ${covidVM.lastSync}"
    }
}