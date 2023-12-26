package com.david.weatherchannel.location

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.setFragmentResult
import com.david.weatherchannel.R
import com.david.weatherchannel.extensions.areLocationPermissionsGranted
import com.david.weatherchannel.extensions.fragmentBooleanResult
import com.david.weatherchannel.utils.Toaster
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class LocationPermissionHandler @Inject constructor(
    private val fragment: Fragment,
    private val locationManager: LocationManager,
    private val toaster: Toaster
) {

    private companion object {
        const val MULTIPLE_PERMISSIONS_REQUEST_KEY = "askedForMultiplePermissions"
        const val MULTIPLE_PERMISSIONS_RESULT_KEY = "askedForMultiplePermissionsResult"
    }

    fun checkLocationPermission(
        onDenied: () -> Unit,
        onGranted: () -> Unit
    ) {
        checkForGPS {
            if (!fragment.requireContext().areLocationPermissionsGranted()) {
                getLocationPermissions(onDenied, onGranted)
            } else {
                onGranted()
            }
        }
    }

    private fun checkForGPS(onLocationAvailable: () -> Unit) {
        if (!isLocationEnabled) {
            toaster.shortToast(R.string.enable_gps)
            fragment.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        } else {
            onLocationAvailable()
        }
    }

    private val isLocationEnabled
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            locationManager.isLocationEnabled
        } else {
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )
        }

    private fun clearMultiplePermissionAlreadyExistingResultListener() {
        fragment.clearFragmentResultListener(MULTIPLE_PERMISSIONS_REQUEST_KEY)
    }

    private fun launchForegroundPermissionResult() {
        multiplePermissions.launch(arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION))
    }

    private val multiplePermissions: ActivityResultLauncher<Array<String>> =
        fragment.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            val granted = result.map { entry -> entry.value }.filter { allowed -> !allowed }
            if (granted.isEmpty()) {
                fragment.setFragmentResult(
                    MULTIPLE_PERMISSIONS_REQUEST_KEY, bundleOf(
                        MULTIPLE_PERMISSIONS_RESULT_KEY to true
                    )
                )
            } else {
                fragment.setFragmentResult(
                    MULTIPLE_PERMISSIONS_REQUEST_KEY, bundleOf(
                        MULTIPLE_PERMISSIONS_RESULT_KEY to false
                    )
                )
            }
        }

    private val accuracyPermissions: ActivityResultLauncher<Array<String>> =
        fragment.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            when {
                permissions.getOrDefault(ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                    fragment.setFragmentResult(
                        MULTIPLE_PERMISSIONS_REQUEST_KEY, bundleOf(
                            MULTIPLE_PERMISSIONS_RESULT_KEY to true
                        )
                    )
                }

                permissions.getOrDefault(ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                    toaster.shortToast(R.string.using_approx_location)
                }

                else -> {
                    // No location access granted.
                    fragment.setFragmentResult(
                        MULTIPLE_PERMISSIONS_REQUEST_KEY, bundleOf(
                            MULTIPLE_PERMISSIONS_RESULT_KEY to false
                        )
                    )
                }
            }
        }

    private fun getLocationPermissions(
        onDenied: () -> Unit = {},
        onGranted: () -> Unit
    ) {
        when {
            Build.VERSION.SDK_INT > Build.VERSION_CODES.R -> {
                accuracyPermissions.launch(arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION))
                clearMultiplePermissionAlreadyExistingResultListener()
                fragment.fragmentBooleanResult(
                    MULTIPLE_PERMISSIONS_REQUEST_KEY,
                    MULTIPLE_PERMISSIONS_RESULT_KEY,
                    onDenied,
                    onGranted
                )
            }

            else -> {
                launchForegroundPermissionResult()
                clearMultiplePermissionAlreadyExistingResultListener()
                fragment.fragmentBooleanResult(
                    MULTIPLE_PERMISSIONS_REQUEST_KEY,
                    MULTIPLE_PERMISSIONS_RESULT_KEY,
                    onDenied,
                    onGranted
                )
            }
        }
    }
}