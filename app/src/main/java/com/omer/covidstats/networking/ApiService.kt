package com.omer.covidstats.networking

import com.omer.covidstats.model.BaseResponce
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    //Http Get method to retrieve summary of COVID19
    //and returns BaseResponce object
    @GET("summary")
    fun getSummary(): Call<BaseResponce>
}