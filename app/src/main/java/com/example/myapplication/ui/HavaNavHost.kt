
package com.example.myapplication.ui

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.city.CityScreen
import com.example.myapplication.ui.forecast.ForecastScreen

@Composable
fun HavaNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "city"
    ) {
        composable(route = "city") {
            CityScreen(
                onCitySelected = { city ->
                    val encodedCityName = Uri.encode(city.name)

                    navController.navigate(
                        "forecast/${city.latitude}/${city.longitude}/$encodedCityName"
                    )
                }
            )
        }

        composable(
            route = "forecast/{latitude}/{longitude}/{cityName}",
            arguments = listOf(
                navArgument("latitude") {
                    type = NavType.FloatType
                },
                navArgument("longitude") {
                    type = NavType.FloatType
                },
                navArgument("cityName") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val latitude = backStackEntry.arguments
                ?.getFloat("latitude")
                ?.toDouble() ?: 0.0

            val longitude = backStackEntry.arguments
                ?.getFloat("longitude")
                ?.toDouble() ?: 0.0

            val cityName = backStackEntry.arguments
                ?.getString("cityName")
                ?.let { Uri.decode(it) }
                ?: ""

            ForecastScreen(
                latitude = latitude,
                longitude = longitude,
                cityName = cityName,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
