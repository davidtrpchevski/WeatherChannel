package com.david.weatherchannel.location

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import com.david.coroutines.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit

class CurrentLocationProvider @Inject constructor(
    private val locationManager: LocationManager,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) {
    companion object {
        private const val MIN_LOCATION_UPDATE_INTERVAL = 1000L
        private const val MIN_LOCATION_DISTANCE_DIFFERENCE = 0F
    }

    private val outdatedLocationThreshold: Long = 10.minutes.toLong(DurationUnit.MILLISECONDS)

    private val locationProvider: String
        get() =
            when {
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) -> LocationManager.GPS_PROVIDER
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) -> LocationManager.NETWORK_PROVIDER
                else -> LocationManager.PASSIVE_PROVIDER
            }

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Location = withContext(mainDispatcher) {

        suspendCancellableCoroutine { continuation ->

            val locationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    locationManager.removeUpdates(this)
                    continuation.resume(location)
                }

                override fun onProviderEnabled(provider: String) {}

                override fun onProviderDisabled(provider: String) {}

                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            }

            try {
                val lastKnownGPSLocation =
                    locationManager.getLastKnownLocation(locationProvider)

                if (lastKnownGPSLocation != null && lastKnownGPSLocation.time > System.currentTimeMillis() - outdatedLocationThreshold) {
                    locationListener.onLocationChanged(lastKnownGPSLocation)
                } else {
                    locationManager.requestLocationUpdates(
                        locationProvider,
                        MIN_LOCATION_UPDATE_INTERVAL,
                        MIN_LOCATION_DISTANCE_DIFFERENCE,
                        locationListener
                    )
                }
                continuation.invokeOnCancellation { locationManager.removeUpdates(locationListener) }
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }
    }
}