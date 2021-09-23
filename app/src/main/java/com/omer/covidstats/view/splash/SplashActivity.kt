package com.omer.covidstats.view.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.omer.covidstats.R
import com.omer.covidstats.utils.ActivityRepo

class SplashActivity : AppCompatActivity() {

    private val splash_timeout: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            ActivityRepo.startDashboardActivity(this)
            finish()
        }, splash_timeout)
    }
}