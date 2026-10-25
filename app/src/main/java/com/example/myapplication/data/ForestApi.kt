
package com.example.myapplication.data


import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {

    @GET("v1/forecast")
    suspend fun getForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String =
            "temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m",
        @Query("hourly") hourly: String =
            "temperature_2m,weather_code",
        @Query("daily") daily: String =
            "weather_code,temperature_2m_max,temperature_2m_min",
        @Query("forecast_days") forecastDays: Int = 7,
        @Query("timezone") timezone: String = "auto"
    ): ForecastResponse
}
