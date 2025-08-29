package com.jnda.openweatherapp.feature.home.repository

import com.jnda.openweatherapp.network.IoDispatcher
import com.jnda.openweatherapp.network.NetworkResult
import com.jnda.openweatherapp.network.requestAPI
import com.jnda.openweatherapp.provider.WeatherAPIProvider
import com.jnda.openweatherapp.storage.dao.WeatherDAO
import com.jnda.openweatherapp.storage.entity.WeatherEntity
import com.jnda.openweatherapp.feature.home.data.WeatherDTO
import com.jnda.openweatherapp.feature.home.data.toDTO
import com.jnda.openweatherapp.feature.home.service.WeatherService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

interface WeatherRepository {
    suspend fun getWeather(latitude: String?, longitude: String?) : WeatherDTO?
    suspend fun refreshWeather(latitude: String?, longitude: String?) : WeatherDTO?
}

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val weatherDAO: WeatherDAO,
    private val weatherAPIProvider: WeatherAPIProvider,
    @param:IoDispatcher
    private val dispatcher: CoroutineDispatcher

): WeatherRepository {
    override suspend fun getWeather(
        latitude: String?,
        longitude: String?
    ): WeatherDTO? {
        val map = HashMap<String, Any>()
        map["lat"] = latitude?.toDouble() ?: "0.0"
        map["lon"] = longitude?.toDouble() ?: "0.0"
        map["units"] = "metric"
        map["appid"] = weatherAPIProvider.getKey()

        var data: WeatherDTO? = null
        return withContext(dispatcher) {
            val result = requestAPI { weatherService.getWeather(map) }
            if (result is NetworkResult.Success) {
                data = result.data?.toDTO()

                val weatherEntity = WeatherEntity(
                    weatherTemp = data?.temp ?: "",
                    dateTime = Date().time.toString(),
                    description = result.data?.weathers?.get(0)?.description ?: "",
                    icon = data?.icon ?: ""
                )

                weatherDAO.insert(weatherEntity)
            }

            data
        }
    }

    override suspend fun refreshWeather(
        latitude: String?,
        longitude: String?
    ): WeatherDTO? {
        val map = HashMap<String, Any>()
        map["lat"] = latitude?.toDouble() ?: "0.0"
        map["lon"] = longitude?.toDouble() ?: "0.0"
        map["units"] = "metric"
        map["appid"] = weatherAPIProvider.getKey()

        var data: WeatherDTO? = null
        return withContext(dispatcher) {
            val result = requestAPI { weatherService.getWeather(map) }
            if (result is NetworkResult.Success) {
                data = result.data?.toDTO()
            }
            data
        }
    }

}