package com.jnda.openweatherapp.feature.register.viewmodel

import com.jnda.openweatherapp.BaseViewModel
import com.jnda.openweatherapp.ui.screens.LoginDestination
import com.jnda.openweatherapp.feature.register.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
): BaseViewModel() {

    fun addUser(username: String, password: String) {
        makeRequest {
            val result = registerRepository.insertUser(username, password)
            if (result > 0) {
                _navigationEvent.send(LoginDestination)
            } else {
                _errorMessage.send("Something went wrong")
            }
        }
    }
}