package com.jnda.openweatherapp.location

data class UserLocationDTO(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    /**
     * A locality from Geocoder Address
     */
    val city: String = "",
    /**
     * A combination of adminArea and countryCode from Geocoder Address
     */
    val countryCode: String = ""
)