
package com.example.myapplication

import com.example.myapplication.data.ApiClient
import org.junit.Assert.assertNotNull
import org.junit.Test

class ForecastApiTest {

    @Test
    fun forecastApiClientIsCreated() {
        assertNotNull(ApiClient.forecastApi)
    }
}
