package com.omer.covidstats.view.dashboard

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.omer.covidstats.R
import com.omer.covidstats.model.BaseResponce
import com.omer.covidstats.utils.HelperSnippets
import com.omer.covidstats.viewmodel.CovidViewModel


class DashboardActivity : AppCompatActivity() {

    private lateinit var summary: BaseResponce
    private lateinit var covidVM: CovidViewModel
    private val pullRefreshVM: PullRefreshViewModel by viewModels()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var bottomNavBar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        configureUI()
        getSummary()
    }


    private fun configureUI() {
        //getting swipeRefreshLayout
        swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            covidVM.getSummary()
        }

        //getting bottom navigation view
        bottomNavBar = findViewById<BottomNavigationView>(R.id.bottom_navbar)
        //getting navHost/Controller
        val navCont = findNavController(R.id.navHost)
        bottomNavBar.setupWithNavController(navCont)

    }

    private fun getSummary() {
        covidVM = ViewModelProviders.of(this).get(CovidViewModel::class.java)
        val data = HelperSnippets.getInstance().retrieveData(this)
        if (data != null) {
            covidVM.lastSync = HelperSnippets.getInstance().getTimestamp(this)
            covidVM.summaryLiveData.value = data
        }
        else {
            covidVM.getSummary()
        }
        setupObservers()
    }

    private fun setupObservers() {
        covidVM.summaryLiveData.observe(this, Observer {
            covidVM.summaryLiveData.value.let {
                HelperSnippets.getInstance().storeData(it!!, this)
            }
            swipeRefreshLayout.isRefreshing = false
        })

        pullRefreshVM.isEnabled.observe(this, Observer {
            swipeRefreshLayout.isEnabled = it
        })
    }
}


class PullRefreshViewModel: ViewModel() {
    var isEnabled = MutableLiveData<Boolean>()

    init {
        isEnabled.value = true
    }
}