
package com.example.myapplication.data

import retrofit2.http.GET
import retrofit2.http.Query

interface CityApi {

    @GET("v1/search")
    suspend fun search(
        @Query("name") cityName: String,
        @Query("count") count: Int = 10,
        @Query("language") language: String = "tr",
        @Query("format") format: String = "json"
    ): CitySearchResponse
}
