package com.jnda.openweatherapp.feature.home.viewmodel

import com.jnda.openweatherapp.BaseViewModel
import com.jnda.openweatherapp.feature.home.data.WeatherDTO
import com.jnda.openweatherapp.feature.home.data.WeatherItemDTO
import com.jnda.openweatherapp.feature.home.repository.WeatherRepository
import com.jnda.openweatherapp.storage.dao.WeatherDAO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val weatherDAO: WeatherDAO
): BaseViewModel() {

    private val _weatherDTO = MutableStateFlow<WeatherDTO?>(null)
    val weatherDTO = _weatherDTO.asStateFlow()

    private val _weatherItemDTO = MutableStateFlow<List<WeatherItemDTO>>(emptyList())
    val weatherItemDTO = _weatherItemDTO.asStateFlow()

    fun getWeather(lat: String, lng: String) {
        makeRequest {
            val result = weatherRepository.getWeather(lat, lng)
            if (result != null) {
                _weatherDTO.update { result }
            }
        }
    }

    fun refreshWeather(lat: String, lng: String) {
        makeRequest {
            val result = weatherRepository.refreshWeather(lat, lng)
            if (result != null) {
                _weatherDTO.update { result }
            }
        }
    }

    fun getWeathers() {
        makeRequest {
            weatherDAO.getWeathers().onEach { list ->
                if (list.isNotEmpty()) {
                    val a = list.map { l ->
                        WeatherItemDTO(
                            l.weatherTemp,
                            l.dateTime,
                            l.description,
                            l.icon,
                        )
                    }
                    _weatherItemDTO.update { a }
                }
            }.collect()
        }
    }
}