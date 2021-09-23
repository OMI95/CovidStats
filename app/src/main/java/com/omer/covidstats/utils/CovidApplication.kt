package com.omer.covidstats.utils

import android.app.Application


class CovidApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initTheme()
    }

    /**
     *
     * The following method calls before every method
     * on app start and set the theme set by user.
     */
    private fun initTheme() {
        val pref = getSharedPreferences("ThemeMode", MODE_PRIVATE)
        HelperSnippets.getInstance().setTheme(pref.getInt("mode",2))
    }
}