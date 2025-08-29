package com.jnda.openweatherapp.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import com.jnda.openweatherapp.ext.orZero
import com.jnda.openweatherapp.network.IoDispatcher
import com.jnda.openweatherapp.location.UserLocationDTO
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

interface LocationProvider {
    fun initLocationProvider()
    fun getDefaultLocation()
    fun isInitialized(): Boolean
    fun isLocationPermissionGranted(): Boolean
    fun getLocationPermissions(): Array<String>
    fun getLastKnownLocation()
    fun startLocationUpdates()
    fun stopLocationUpdates()
    fun getLatitude(): Double
    fun getLongitude(): Double
    fun addOnLocationResult(onLocationResult: (dto: UserLocationDTO) -> Unit)
}

class LocationProviderImpl @Inject constructor(
    @param:ApplicationContext
    private val context: Context,
    @param:IoDispatcher
    private val dispatcher: CoroutineDispatcher
): LocationProvider {
    companion object {
        private const val TAG = "LocationProviderImpl"
        private const val defaultLatitude = 14.5496299
        private const val defaultLongitude = 121.0470886
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest

    private var latitude : Double? = null
    private var longitude: Double? = null

    private var onLocationResult: (dto: UserLocationDTO) -> Unit = { _ ->  }

    override fun initLocationProvider() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations){
                    // Update UI with location data
                    latitude = location.latitude
                    longitude = location.longitude
                    getAddress()
                    Log.i(TAG, "onLocationResult called latitude: $latitude, longitude: $longitude")
                }
            }
        }
        createLocationRequest()
        getLastKnownLocation()
    }

    override fun getDefaultLocation() {
        latitude = defaultLatitude
        longitude = defaultLongitude
        getAddress()
    }

    override fun isInitialized(): Boolean = ::fusedLocationProviderClient.isInitialized

    override fun isLocationPermissionGranted(): Boolean {
        val permissionAccessFineLocation = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val permissionAccessCoarseLocation = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        return permissionAccessFineLocation || permissionAccessCoarseLocation
    }

    override fun getLocationPermissions(): Array<String> =
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

    override fun getLastKnownLocation() {
        if (isLocationPermissionGranted()) {
            // adding this just to remove the error warning
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
                return
            }
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { result ->
                result?.let { location ->
                    latitude = location.latitude
                    longitude = location.longitude
                    getAddress()
                    Log.i(TAG, "lastLocation called latitude: $latitude, longitude: $longitude")
                }
            }
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
            .setMinUpdateIntervalMillis(5000)
            .setMaxUpdateDelayMillis(100)
            .build()

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(context)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener { locationSettingsResponse ->
            // All location settings are satisfied. The client can initialize
            // location requests here.
            // ...
            startLocationUpdates()
        }
        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException){
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                Log.i(TAG, "ResolvableApiException occurred: $exception")
            }
        }
    }

    override fun startLocationUpdates() {
        if (isLocationPermissionGranted()) {
            // adding this just to remove the error warning
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
                return
            }
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        }
    }

    override fun stopLocationUpdates() {
        if (::fusedLocationProviderClient.isInitialized) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }
    }

    override fun getLatitude(): Double = latitude.orZero()
    override fun getLongitude(): Double = longitude.orZero()

    override fun addOnLocationResult(onLocationResult: (dto: UserLocationDTO) -> Unit) {
        this.onLocationResult = onLocationResult
    }

    private fun getAddress() {
        runBlocking(dispatcher) {
            try {
                val geocoder = Geocoder(context)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    geocoder.getFromLocation(getLatitude(), getLongitude(), 1) { result ->
                        result.firstOrNull()?.let { address ->
                            invokeAddress(address)
                        }
                    }
                } else {
                    geocoder.getFromLocation(
                        getLatitude(),
                        getLongitude(), 1
                    )?.firstOrNull()?.let { address ->
                        invokeAddress(address)
                    }
                }
            } catch (ex: Exception) {
                // possible - IOException
                ex.printStackTrace()
            }
        }
    }

    private fun invokeAddress(address: Address) {
        val city = address.locality?.let { locality ->
            if (!locality.lowercase().contains("city")) {
                return@let "$locality City"
            }
            return@let locality
        } ?: "NA"
        onLocationResult.invoke(
            UserLocationDTO(
                getLatitude(), getLongitude(),
                city = city,
                countryCode = "${address.adminArea ?: "NA"}\n${address.countryName ?: "NA"}"
            )
        )
    }

}