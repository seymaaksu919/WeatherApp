
package com.example.myapplication

import com.example.myapplication.data.WeatherRepository
import com.example.myapplication.model.Forecast
import com.example.myapplication.ui.LoadState
import com.example.myapplication.ui.forecast.ForecastViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ForecastViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createForecast(currentExists: Boolean = true) =
        Forecast(
            latitude = 41.0082,
            longitude = 28.9784,
            current = if (currentExists) {
                null
            } else {
                null
            },
            hourly = null,
            daily = null
        )

    @Test
    fun repositoryExceptionReturnsErrorState() = runTest {
        val fakeRepository = object : WeatherRepository() {
            override suspend fun getForecast(
                latitude: Double,
                longitude: Double
            ): Forecast {
                throw Exception("Test hatası")
            }
        }

        val viewModel = ForecastViewModel(fakeRepository)

        viewModel.loadForecast(41.0082, 28.9784)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is LoadState.Error)

        if (state is LoadState.Error) {
            assertEquals("Test hatası", state.message)
        }
    }
}
