package com.jnda.openweatherapp.feature.home.repository

import com.jnda.openweatherapp.MainDispatcherRule
import com.jnda.openweatherapp.feature.home.data.WeatherDTO
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepositoryTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    val weatherRepository: WeatherRepository = mockk()

    @Before
    fun setup() {
        coEvery { weatherRepository.getWeather(any(), any()) } returns WeatherDTO(
            temp = "30.0",
            sunriseTime = "06:00 AM",
            sunsetTime = "05:30 PM",
            icon = "01d"
        )

        coEvery { weatherRepository.getWeather(any(), any()) } returns WeatherDTO(
            temp = "27.0",
            sunriseTime = "05:40 AM",
            sunsetTime = "06:20 PM",
            icon = "02d"
        )
    }

    @Test
    fun `test getWeather success`() = runTest {
        val weather = weatherRepository.getWeather("14.5995", "120.9842")

        assertEquals("27.0", weather?.temp)
        assertEquals("02d", weather?.icon)
    }
}