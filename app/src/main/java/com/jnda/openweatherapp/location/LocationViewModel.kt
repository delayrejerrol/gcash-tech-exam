package com.jnda.openweatherapp.location

import com.jnda.openweatherapp.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationProvider: LocationProvider
): BaseViewModel() {

    private val _userLocationDTO = MutableStateFlow<UserLocationDTO>(UserLocationDTO())
    val userLocationDTO = _userLocationDTO.asStateFlow()

    init {
        locationProvider.addOnLocationResult {
            updateLocation(it)
            locationProvider.stopLocationUpdates()
        }
    }

    fun checkLocationPermission(
        onLaunchPermission: (Array<String>) -> Unit
    ) {
        if (locationProvider.isLocationPermissionGranted()) {
            if (locationProvider.isInitialized()) {
                locationProvider.startLocationUpdates()
            } else {
                locationProvider.initLocationProvider()
            }
        } else {
            onLaunchPermission.invoke(locationProvider.getLocationPermissions())
        }
    }

    fun updateLocation(dto: UserLocationDTO) {
        _userLocationDTO.update { dto }
    }

    fun getDefaultLocation() {
        locationProvider.getDefaultLocation()
    }

    fun getLocationPermissions(): Array<String> {
        return locationProvider.getLocationPermissions()
    }

}