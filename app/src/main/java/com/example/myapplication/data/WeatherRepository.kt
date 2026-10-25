
package com.example.myapplication.data

import com.example.myapplication.model.CurrentWeather
import com.example.myapplication.model.DailyWeather
import com.example.myapplication.model.Forecast
import com.example.myapplication.model.HourlyWeather

open class WeatherRepository {

    open suspend fun getForecast(
        latitude: Double,
        longitude: Double
    ): Forecast {

        val response = ApiClient.forecastApi.getForecast(
            latitude = latitude,
            longitude = longitude
        )

        val current = response.current?.let {
            CurrentWeather(
                time = it.time,
                temperature = it.temperature,
                relativeHumidity = it.relativeHumidity,
                weatherCode = it.weatherCode,
                windSpeed = it.windSpeed
            )
        }

        val hourly = response.hourly?.let {
            HourlyWeather(
                time = it.time,
                temperatures = it.temperatures,
                weatherCodes = it.weatherCodes
            )
        }

        val daily = response.daily?.let {
            DailyWeather(
                dates = it.time,
                weatherCodes = it.weatherCodes,
                maxTemperatures = it.maxTemperatures,
                minTemperatures = it.minTemperatures
            )
        }

        return Forecast(
            latitude = response.latitude,
            longitude = response.longitude,
            current = current,
            hourly = hourly,
            daily = daily
        )
    }
}
