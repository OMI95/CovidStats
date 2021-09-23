package com.omer.covidstats.utils

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import androidx.appcompat.app.AppCompatDelegate
import com.google.gson.Gson
import com.omer.covidstats.model.BaseResponce
import java.text.SimpleDateFormat

//Helper class for custom and global functions
class HelperSnippets {
    companion object {
        private var INSTANCE: HelperSnippets? = null
        fun getInstance() = INSTANCE
            ?: HelperSnippets().also {
                INSTANCE = it
            }
    }

    /**
     * @param data for api respoonce data
     * @param activity For activity object
     *
     * The following function convert data class object to
     * json string and store it into shared preferences
     */
    fun storeData(data: BaseResponce, activity: Activity) {
        val pref = activity.getPreferences(MODE_PRIVATE) ?: return
        with(pref.edit()) {
            val gson = Gson()
            val jsonData = gson.toJson(data)
            putString("COVIDData", jsonData)
            putLong("LastSync", System.currentTimeMillis())
            apply()
        }
    }

    /**
     * @param activity For activity object
     *
     * The following method reterive api stored json string
     * and converts back to data class object
     */
    fun retrieveData(activity: Activity): BaseResponce? {
        var data: BaseResponce?
        val gson = Gson()
        val pref = activity.getPreferences(MODE_PRIVATE)
        val jsonData = pref.getString("COVIDData", "").toString()
        data = gson.fromJson(jsonData, BaseResponce::class.java)
        return data
    }

    /**
     * @param activity For activity object
     *
     * The following function retrieves timestamped
     * stored in shared preferences
     */
    fun getTimestamp(activity: Activity): String {
        val pref = activity.getPreferences(MODE_PRIVATE)
        return getSDateFormat(pref.getLong("LastSync", 0))
    }

    fun getSDateFormat(timeStamp: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yy hh:mm a")
        return sdf.format(timeStamp)
    }

    /**
     * @param mode for DarkTheme mode
     *
     * The following method apply the dark theme
     * throughout the app.
     */
    fun setTheme(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    /**
     * @param number for formating
     *
     * The following method format the given
     * number by putting commas for thousands
     */
    fun formatStringWithCommas(number: Int): String {
        return String.format("%,d", number)
    }
}