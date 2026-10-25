
package com.example.myapplication

import com.example.myapplication.data.ApiClient
import org.junit.Assert.assertNotNull
import org.junit.Test

class CityApiTest {

    @Test
    fun cityApiClientIsCreated() {
        assertNotNull(ApiClient.cityApi)
    }
}
