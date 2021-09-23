package com.omer.covidstats.utils

import android.content.Context
import android.content.Intent
import com.omer.covidstats.view.dashboard.DashboardActivity

object ActivityRepo {
    /**
     * @param context
     *
     * The following method starts the
     * Dashboard Activity.
     */
    fun startDashboardActivity(context: Context) {
        context.startActivity(Intent(context, DashboardActivity::class.java))
    }
}