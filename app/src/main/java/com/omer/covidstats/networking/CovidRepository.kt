package com.omer.covidstats.networking

import com.omer.covidstats.model.BaseResponce
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class CovidRepository {
    companion object {
        private var INSTANCE: CovidRepository? = null
        fun getInstance() = INSTANCE
            ?: CovidRepository().also {
                INSTANCE = it
            }
    }

    //Get Covid Summary
    fun getSummary(onResult: (isSuccess: Boolean, response: BaseResponce?) -> Unit) {
        ApiClient.client.getSummary().enqueue(object : Callback<BaseResponce> {
            override fun onResponse(call: Call<BaseResponce>?, response: Response<BaseResponce>?) {
                if (response != null && response.isSuccessful)
                    onResult(true, response.body()!!)
                else
                    onResult(false, null)
            }

            override fun onFailure(call: Call<BaseResponce>?, t: Throwable?) {
                onResult(false, null)
            }
        })
    }

}