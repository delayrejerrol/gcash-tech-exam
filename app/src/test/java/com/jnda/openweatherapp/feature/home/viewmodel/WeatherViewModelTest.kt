package com.jnda.openweatherapp.feature.home.viewmodel

import com.jnda.openweatherapp.feature.home.data.WeatherDTO
import com.jnda.openweatherapp.feature.home.repository.WeatherRepository
import com.jnda.openweatherapp.storage.dao.WeatherDAO
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var weatherRepository: WeatherRepository
    private lateinit var weatherDao: WeatherDAO
    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        weatherRepository = mockk() // or Mockito.mock(WeatherRepository::class.java)
        weatherDao = mockk()

        viewModel = WeatherViewModel(
            weatherRepository,
            weatherDao
        )
    }

    @Test
    fun `getWeather should update LiveData`() = runTest {
        val fakeWeather = WeatherDTO(/* your test data */)

        coEvery { weatherRepository.getWeather("10", "20") } returns fakeWeather

        viewModel.getWeather("10", "20")

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(fakeWeather,
            viewModel.weatherDTO.value)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}