
package com.example.myapplication.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val GEOCODING_BASE_URL =
        "https://geocoding-api.open-meteo.com/"

    private const val WEATHER_BASE_URL =
        "https://api.open-meteo.com/"

    private val geocodingRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(GEOCODING_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val weatherRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val cityApi: CityApi by lazy {
        geocodingRetrofit.create(CityApi::class.java)
    }

    val forecastApi: ForecastApi by lazy {
        weatherRetrofit.create(ForecastApi::class.java)
    }
}
