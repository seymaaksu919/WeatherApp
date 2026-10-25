
package com.example.myapplication.model

data class Forecast(
    val latitude: Double,
    val longitude: Double,
    val current: CurrentWeather?,
    val hourly: HourlyWeather?,
    val daily: DailyWeather?
)

data class CurrentWeather(
    val time: String,
    val temperature: Double,
    val relativeHumidity: Int,
    val weatherCode: Int,
    val windSpeed: Double
)

data class HourlyWeather(
    val time: List<String>,
    val temperatures: List<Double>,
    val weatherCodes: List<Int>
)

data class DailyWeather(
    val dates: List<String>,
    val weatherCodes: List<Int>,
    val maxTemperatures: List<Double>,
    val minTemperatures: List<Double>
)
