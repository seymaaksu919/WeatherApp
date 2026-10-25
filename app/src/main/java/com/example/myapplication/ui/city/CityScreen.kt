
package com.example.myapplication.ui.city

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.model.City
import com.example.myapplication.ui.LoadState
import com.example.myapplication.ui.component.EmptyView
import com.example.myapplication.ui.component.ErrorView
import com.example.myapplication.ui.component.ShimmerList

@Composable
fun CityScreen(
    onCitySelected: (City) -> Unit,
    cityViewModel: CityViewModel = viewModel()
) {
    var searchText by rememberSaveable {
        mutableStateOf("")
    }

    var selectedCity by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    val uiState by cityViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F7FF))
            .padding(16.dp)
    ) {
        Text(
            text = "Hava Durumu",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF193B68)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Şehrini ara, konum bilgilerini keşfet.",
            fontSize = 14.sp,
            color = Color(0xFF66758A)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = {
                Text("Şehir ara")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                selectedCity = null
                cityViewModel.searchCity(searchText.trim())
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ara")
        }

        selectedCity?.let { cityName ->
            Text(
                text = "Seçilen şehir: $cityName",
                modifier = Modifier.padding(vertical = 8.dp),
                color = Color(0xFF193B68),
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

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
                        message = "Şehirler yükleniyor..."
                    )
                }
            }

            is LoadState.Success -> {
                val cities = state.data

                Text(
                    text = "Bulunan şehirler: ${cities.size}",
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF193B68)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(
                        items = cities,
                        key = { city ->
                            "${city.name}_${city.country}_${city.latitude}_${city.longitude}"
                        }
                    ) { city ->
                        CityCard(
                            city = city,
                            isSelected = selectedCity == city.name,
                            onClick = {
                                selectedCity = city.name
                                onCitySelected(city)
                            }
                        )
                    }
                }
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
                        message = if (searchText.isBlank()) {
                            "Şehir aramak için bir isim gir."
                        } else {
                            "Şehir bulunamadı. İsmi kontrol edip tekrar dene."
                        }
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
                        message = "Şehirler yüklenemedi: ${state.message}",
                        onRetry = {
                            cityViewModel.searchCity(
                                searchText.trim()
                            )
                        }
                    )
                }
            }
        }
    }
}
