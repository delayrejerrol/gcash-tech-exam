package com.jnda.openweatherapp.feature.home.data

import com.google.gson.annotations.SerializedName


data class WeatherRsp(
    @SerializedName("weather") val weathers: List<Weather>?,
    @SerializedName("base") val base: String?,
    @SerializedName("main") val mainWeather: WeatherMain,
    @SerializedName("sys") val weatherSys: WeatherSys,
    @SerializedName("timezone") val timeZone: Int
)

data class Weather(
    @SerializedName("id") val id: Int?,
    @SerializedName("main") val main: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("icon") val icon: String?,
)

data class WeatherMain(
    @SerializedName("temp") val temp: Double?,
    @SerializedName("temp_min") val minTemp: Double?,
    @SerializedName("temp_max") val maxTemp: Double?
)

data class WeatherSys(
    @SerializedName("type") val type: Int?,
    @SerializedName("id") val id: Long?,
    @SerializedName("country") val country: String?,
    @SerializedName("sunrise") val sunrise: Long?,
    @SerializedName("sunset") val sunset: Long?
)
