package com.jnda.openweatherapp.network

sealed class NetworkResult<out T>() {
    data class Success<T>(val data: T?) : NetworkResult<T>()
    data class Failure(val errCode: String?, val errMessage: String?): NetworkResult<Nothing>()
}