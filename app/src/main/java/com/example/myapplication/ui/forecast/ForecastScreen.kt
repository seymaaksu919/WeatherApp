
package com.example.myapplication.ui.forecast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.model.DailyWeather
import com.example.myapplication.model.Forecast
import com.example.myapplication.model.HourlyWeather
import com.example.myapplication.model.WeatherCondition
import com.example.myapplication.model.weatherConditionFromCode
import com.example.myapplication.ui.LoadState
import com.example.myapplication.ui.component.EmptyView
import com.example.myapplication.ui.component.ErrorView
import com.example.myapplication.ui.component.ShimmerList
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ForecastScreen(
    cityName: String,
    latitude: Double,
    longitude: Double,
    onBack: () -> Unit,
    forecastViewModel: ForecastViewModel = viewModel()
) {
    val uiState by forecastViewModel.uiState.collectAsState()

    LaunchedEffect(latitude, longitude) {
        forecastViewModel.loadForecast(
            latitude = latitude,
            longitude = longitude
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F7FF))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Text(
                    text = "‹",
                    fontSize = 36.sp,
                    color = Color(0xFF193B68)
                )
            }

            Column {
                Text(
                    text = cityName,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF193B68)
                )
                Text(
                    text = "Hava durumu tahmini",
                    fontSize = 14.sp,
                    color = Color(0xFF66758A)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = uiState) {
            is LoadState.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ShimmerList(
                        message = "Hava durumu yükleniyor..."
                    )
                }
            }

            is LoadState.Success -> {
                ForecastContent(
                    forecast = state.data
                )
            }

            is LoadState.Empty -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    EmptyView(
                        message = "Hava durumu verisi bulunamadı."
                    )
                }
            }

            is LoadState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ErrorView(
                        message = "Hava durumu yüklenemedi: ${state.message}",
                        onRetry = {
                            forecastViewModel.loadForecast(
                                latitude = latitude,
                                longitude = longitude
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ForecastContent(
    forecast: Forecast
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        forecast.current?.let { current ->
            item {
                CurrentWeatherCard(
                    temperature = current.temperature,
                    weatherCode = current.weatherCode,
                    humidity = current.relativeHumidity,
                    windSpeed = current.windSpeed
                )
            }
        }

        forecast.hourly?.let { hourly ->
            item {
                HourlyForecastSection(hourly)
            }
        }

        forecast.daily?.let { daily ->
            item {
                Text(
                    text = "7 Günlük Tahmin",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF193B68)
                )
            }

            items(daily.dates.indices.toList()) { index ->
                DailyForecastCard(
                    date = daily.dates[index],
                    weatherCode = daily.weatherCodes.getOrElse(index) { 0 },
                    maxTemperature = daily.maxTemperatures.getOrElse(index) { 0.0 },
                    minTemperature = daily.minTemperatures.getOrElse(index) { 0.0 }
                )
            }
        }
    }
}

@Composable
private fun CurrentWeatherCard(
    temperature: Double,
    weatherCode: Int,
    humidity: Int,
    windSpeed: Double
) {
    val condition = weatherConditionFromCode(weatherCode)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF193B68)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = condition.emoji,
                fontSize = 64.sp
            )

            Text(
                text = "${temperature.toInt()}°C",
                fontSize = 46.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = condition.description,
                fontSize = 18.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                WeatherDetail(
                    title = "Nem",
                    value = "%$humidity"
                )

                WeatherDetail(
                    title = "Rüzgâr",
                    value = "${windSpeed.toInt()} km/sa"
                )
            }
        }
    }
}

@Composable
private fun WeatherDetail(
    title: String,
    value: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = title,
            color = Color(0xFFD8E8FF),
            fontSize = 14.sp
        )

        Text(
            text = value,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun HourlyForecastSection(
    hourly: HourlyWeather
) {
    Column {
        Text(
            text = "Saatlik Tahmin",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF193B68)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val count = minOf(
                24,
                hourly.time.size,
                hourly.temperatures.size,
                hourly.weatherCodes.size
            )

            for (index in 0 until count) {
                val condition =
                    weatherConditionFromCode(hourly.weatherCodes[index])

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = formatHour(hourly.time[index]),
                            color = Color(0xFF66758A)
                        )

                        Text(
                            text = condition.emoji,
                            fontSize = 28.sp
                        )

                        Text(
                            text = "${hourly.temperatures[index].toInt()}°",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF193B68)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DailyForecastCard(
    date: String,
    weatherCode: Int,
    maxTemperature: Double,
    minTemperature: Double
) {
    val condition = weatherConditionFromCode(weatherCode)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = formatDate(date),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF193B68)
                )

                Text(
                    text = condition.description,
                    color = Color(0xFF66758A),
                    fontSize = 13.sp
                )
            }

            Text(
                text = condition.emoji,
                fontSize = 28.sp
            )

            Text(
                text = "${maxTemperature.toInt()}° / ${minTemperature.toInt()}°",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF193B68)
            )
        }
    }
}

private fun formatHour(dateTime: String): String {
    return try {
        dateTime.substringAfter("T").take(5)
    } catch (_: Exception) {
        dateTime
    }
}

private fun formatDate(date: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM EEE", Locale("tr", "TR"))
        outputFormat.format(inputFormat.parse(date)!!)
    } catch (_: Exception) {
        date
    }
}
