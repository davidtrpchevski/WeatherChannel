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

class CurrentLocationProvider @Inject constructor(
    private val locationManager: LocationManager,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) {
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
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0,
                    0F,
                    locationListener
                )
                continuation.invokeOnCancellation { locationManager.removeUpdates(locationListener) }
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }
    }
}