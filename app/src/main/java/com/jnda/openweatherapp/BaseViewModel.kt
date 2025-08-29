package com.jnda.openweatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jnda.openweatherapp.ui.screens.CommonDestination
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

open class BaseViewModel: ViewModel() {

    protected val _navigationEvent = Channel<CommonDestination>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    protected val _errorMessage = Channel<String>()
    val errorMessage = _errorMessage.receiveAsFlow()

    protected fun makeRequest(unit: suspend() -> Unit) {
        viewModelScope.launch {
            try {
                unit.invoke()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}