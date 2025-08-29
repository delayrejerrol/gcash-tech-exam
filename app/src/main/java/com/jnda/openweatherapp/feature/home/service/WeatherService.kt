package com.jnda.openweatherapp.feature.home.service

import com.jnda.openweatherapp.feature.home.data.WeatherRsp
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherService {
    @GET("weather")
    suspend fun getWeather(
        @QueryMap(encoded = true) query: HashMap<String, Any>
    ): WeatherRsp
}