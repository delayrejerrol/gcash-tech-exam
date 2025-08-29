package com.jnda.openweatherapp.network

suspend fun <T> requestAPI(service: suspend () -> T): NetworkResult<T> {
    return try {
        val result = service()
        NetworkResult.Success(result)
    } catch (e: Exception) {
        NetworkResult.Failure(errCode = "", errMessage = "")
    }
}