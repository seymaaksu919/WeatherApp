
package com.example.myapplication.ui.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.CityRepository
import com.example.myapplication.model.City
import com.example.myapplication.ui.LoadState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CityViewModel(
    private val repository: CityRepository = CityRepository()
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<LoadState<List<City>>>(LoadState.Empty)

    val uiState: StateFlow<LoadState<List<City>>> =
        _uiState.asStateFlow()

    fun searchCity(cityName: String) {
        if (cityName.isBlank()) {
            _uiState.value = LoadState.Empty
            return
        }

        viewModelScope.launch {
            _uiState.value = LoadState.Loading

            try {
                val cities = repository.search(cityName.trim())

                _uiState.value = if (cities.isEmpty()) {
                    LoadState.Empty
                } else {
                    LoadState.Success(cities)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = LoadState.Error(
                    e.message ?: "Şehirler yüklenemedi."
                )
            }
        }
    }
}
