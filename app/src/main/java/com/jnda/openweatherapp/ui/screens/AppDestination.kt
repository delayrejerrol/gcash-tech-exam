package com.jnda.openweatherapp.ui.screens

import androidx.annotation.DrawableRes
import com.jnda.openweatherapp.R

interface CommonDestination {
    @get:DrawableRes val id: Int?
    val route: String
    val name: String
}

object HomeDestination: CommonDestination {
    override val id: Int? = null
    override val route: String = "home"
    override val name: String = "Home"
}

object WeatherMainDestination: CommonDestination {
    override val id: Int? = R.drawable.outline_navigation_24
    override val route: String = "weather_main"
    override val name: String = "Current Weather"
}

object WeatherListDestination: CommonDestination {
    override val id: Int? = R.drawable.outline_checklist_24
    override val route: String = "weather_list"
    override val name: String = "Previous Weathers"
}

object LoginDestination: CommonDestination {
    override val id: Int? = null
    override val route: String = "login"
    override val name: String = "Login"
}

object RegisterDestination: CommonDestination {
    override val id: Int? = null
    override val route: String = "register"
    override val name: String = "Register"
}
