package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class City(
    val name: String,
    val country: String?,
    val latitude: Double,
    val longitude: Double,

    @SerializedName("country_code")
    val countryCode: String? = null
)