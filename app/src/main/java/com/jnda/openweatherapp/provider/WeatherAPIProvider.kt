package com.jnda.openweatherapp.provider

import javax.inject.Inject

interface WeatherAPIProvider {
    fun getKey(): String
}

class WeatherAPIProviderImpl @Inject constructor(): WeatherAPIProvider {
    override fun getKey(): String = "" // PUT YOUR OPENWEATHER API HERE
}

