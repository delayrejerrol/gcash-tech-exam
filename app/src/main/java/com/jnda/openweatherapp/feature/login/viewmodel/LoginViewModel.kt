package com.jnda.openweatherapp.feature.login.viewmodel

import com.jnda.openweatherapp.BaseViewModel
import com.jnda.openweatherapp.ui.screens.WeatherMainDestination
import com.jnda.openweatherapp.feature.login.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
): BaseViewModel() {

    fun login(username: String, password: String) {
        makeRequest {
            val result = loginRepository.findByAccount(username, password)
            if (result != null) {
                _navigationEvent.send(WeatherMainDestination)
            } else {
                _errorMessage.send("Invalid username or password")
            }
        }
    }
}