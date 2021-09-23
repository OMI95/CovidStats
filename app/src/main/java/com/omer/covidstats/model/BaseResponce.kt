package com.omer.covidstats.model

import com.google.gson.annotations.SerializedName

data class BaseResponce (
    @SerializedName("Message")
    val message : String,
    @SerializedName("Global")
    val global : Global,
    @SerializedName("Countries")
    val countries : List<Countries>,
    @SerializedName("Date")
    val date : String
)