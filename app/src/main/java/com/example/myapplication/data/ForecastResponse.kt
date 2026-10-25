
package com.example.myapplication.data

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    val latitude: Double,
    val longitude: Double,
    val current: CurrentResponse?,
    val hourly: HourlyResponse?,
    val daily: DailyResponse?
)

data class CurrentResponse(
    val time: String,
    @SerializedName("temperature_2m")
    val temperature: Double,
    @SerializedName("relative_humidity_2m")
    val relativeHumidity: Int,
    @SerializedName("weather_code")
    val weatherCode: Int,
    @SerializedName("wind_speed_10m")
    val windSpeed: Double
)

data class HourlyResponse(
    val time: List<String>,
    @SerializedName("temperature_2m")
    val temperatures: List<Double>,
    @SerializedName("weather_code")
    val weatherCodes: List<Int>
)

data class DailyResponse(
    val time: List<String>,
    @SerializedName("weather_code")
    val weatherCodes: List<Int>,
    @SerializedName("temperature_2m_max")
    val maxTemperatures: List<Double>,
    @SerializedName("temperature_2m_min")
    val minTemperatures: List<Double>
)
