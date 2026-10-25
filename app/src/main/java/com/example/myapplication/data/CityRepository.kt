
package com.example.myapplication.data

import com.example.myapplication.model.City

open class CityRepository {

    open suspend fun search(cityName: String): List<City> {
        val response = ApiClient.cityApi.search(cityName)
        return response.results.orEmpty()
    }
}
