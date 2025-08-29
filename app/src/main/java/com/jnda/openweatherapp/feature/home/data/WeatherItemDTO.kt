package com.jnda.openweatherapp.feature.home.data

data class WeatherItemDTO(
    val temp: String,
    val dateTime: String,
    val description: String,
    val icon: String
) {
    fun getIconUrl() : String {
        return "https://openweathermap.org/img/wn/$icon@2x.png"
    }
}
