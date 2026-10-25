
package com.example.myapplication.ui.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.WeatherRepository
import com.example.myapplication.model.Forecast
import com.example.myapplication.ui.LoadState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ForecastViewModel(
    private val repository: WeatherRepository = WeatherRepository()
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<LoadState<Forecast>>(LoadState.Empty)

    val uiState: StateFlow<LoadState<Forecast>> =
        _uiState.asStateFlow()

    fun loadForecast(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _uiState.value = LoadState.Loading

            try {
                val forecast = repository.getForecast(
                    latitude = latitude,
                    longitude = longitude
                )

                _uiState.value = if (forecast.current != null) {
                    LoadState.Success(forecast)
                } else {
                    LoadState.Empty
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = LoadState.Error(
                    e.message ?: "Hava durumu yüklenemedi."
                )
            }
        }
    }
}
