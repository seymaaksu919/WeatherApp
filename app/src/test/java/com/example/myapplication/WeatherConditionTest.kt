
package com.example.myapplication

import com.example.myapplication.model.weatherConditionFromCode
import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherConditionTest {

    @Test
    fun clearWeatherCodeReturnsCorrectDescription() {
        val result = weatherConditionFromCode(0)

        assertEquals("☀️", result.emoji)
        assertEquals("Açık", result.description)
    }

    @Test
    fun rainyWeatherCodeReturnsCorrectDescription() {
        val result = weatherConditionFromCode(63)

        assertEquals("🌧️", result.emoji)
        assertEquals("Yağmurlu", result.description)
    }

    @Test
    fun foggyWeatherCodeReturnsCorrectDescription() {
        val result = weatherConditionFromCode(45)

        assertEquals("🌫️", result.emoji)
        assertEquals("Sisli", result.description)
    }

    @Test
    fun unknownWeatherCodeReturnsDefaultDescription() {
        val result = weatherConditionFromCode(999)

        assertEquals("🌡️", result.emoji)
        assertEquals("Bilinmeyen hava durumu", result.description)
    }
}
