
package com.example.myapplication.model

data class WeatherCondition(
    val emoji: String,
    val description: String
)

fun weatherConditionFromCode(code: Int): WeatherCondition {
    return when (code) {
        0 -> WeatherCondition("☀️", "Açık")
        1 -> WeatherCondition("🌤️", "Çoğunlukla açık")
        2 -> WeatherCondition("⛅", "Parçalı bulutlu")
        3 -> WeatherCondition("☁️", "Kapalı")

        45, 48 -> WeatherCondition("🌫️", "Sisli")

        51, 53, 55 -> WeatherCondition("🌦️", "Çisenti")
        56, 57 -> WeatherCondition("🌧️", "Dondurucu çisenti")

        61, 63, 65 -> WeatherCondition("🌧️", "Yağmurlu")
        66, 67 -> WeatherCondition("🌧️", "Dondurucu yağmur")

        71, 73, 75 -> WeatherCondition("❄️", "Kar yağışlı")
        77 -> WeatherCondition("🌨️", "Kar taneleri")

        80, 81, 82 -> WeatherCondition("🌧️", "Sağanak yağış")
        85, 86 -> WeatherCondition("🌨️", "Kar sağanağı")

        95 -> WeatherCondition("⛈️", "Gök gürültülü fırtına")
        96, 99 -> WeatherCondition("⛈️", "Dolu ihtimalli fırtına")

        else -> WeatherCondition("🌡️", "Bilinmeyen hava durumu")
    }
}
