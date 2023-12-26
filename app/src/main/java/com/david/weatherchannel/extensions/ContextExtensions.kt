package com.david.weatherchannel.extensions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat

fun Context.areLocationPermissionsGranted(): Boolean = arePermissionsGranted(
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_FINE_LOCATION
)

fun Context.arePermissionsGranted(vararg permissions: String): Boolean =
    permissions.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

val Context.locationManager
    get() = getSystemService(Context.LOCATION_SERVICE) as LocationManager