package com.omer.covidstats.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.omer.covidstats.model.BaseResponce
import com.omer.covidstats.networking.CovidRepository
import com.omer.covidstats.utils.HelperSnippets

class CovidViewModel : ViewModel() {
    var summaryLiveData = MutableLiveData<BaseResponce>()
    var lastSync: String = ""

    /**
     * The following method invokes the getSummary method from
     * Repository class and on success update the value of livedata object
     */
    fun getSummary() {
        CovidRepository.getInstance().getSummary { isSuccess, response ->
            if (isSuccess) {
                response?.let {
                    lastSync = HelperSnippets.getInstance().getSDateFormat(System.currentTimeMillis())
                    summaryLiveData.value = response
                }
            }
        }
    }
}