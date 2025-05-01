package com.example.hardwarecheck.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import java.util.Locale

object LocationUtil {
    private const val TAG = "LocationUtil"
    private const val LOCATION_TIMEOUT_MS = 10000L // 10 seconds timeout

    fun getCityAndCountry(context: Context, onResult: (String) -> Unit) {
        if (!hasLocationPermission(context)) {
            Log.w(TAG, "Location permission not granted")
            onResult("Sijaintilupia ei ole myönnetty")
            return
        }

        requestLocationWithPermissionCheck(context, onResult)
    }

    @SuppressLint("MissingPermission") // We check permission before calling this
    private fun requestLocationWithPermissionCheck(context: Context, onResult: (String) -> Unit) {
        Log.d(TAG, "Requesting location with permission check...")

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val cancellationTokenSource = CancellationTokenSource()
        val handler = Handler(Looper.getMainLooper())

        // Set up timeout mechanism
        val timeoutRunnable = Runnable {
            Log.w(TAG, "Location request timed out")
            cancellationTokenSource.cancel()
            handler.post { onResult("Sijaintia ei saatavilla (timeout)") }
        }

        handler.postDelayed(timeoutRunnable, LOCATION_TIMEOUT_MS)

        // Try to get last known location first (faster)
        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        ).addOnSuccessListener { location ->
            handler.removeCallbacks(timeoutRunnable)
            if (location != null) {
                processLocation(context, location, onResult)
            } else {
                // If last location is null, request updates
                requestLocationUpdates(fusedLocationClient, handler, timeoutRunnable, context, onResult)
            }
        }.addOnFailureListener { e ->
            handler.removeCallbacks(timeoutRunnable)
            Log.e(TAG, "Error getting last location", e)
            // Fall back to requesting updates
            requestLocationUpdates(fusedLocationClient, handler, timeoutRunnable, context, onResult)
        }
    }

    private fun hasLocationPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdates(
        fusedLocationClient: FusedLocationProviderClient,
        handler: Handler,
        timeoutRunnable: Runnable,
        context: Context,
        onResult: (String) -> Unit
    ) {
        Log.d(TAG, "Requesting location updates...")

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            1000L
        ).setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(500)
            .setMaxUpdateDelayMillis(1000)
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                handler.removeCallbacks(timeoutRunnable)
                Log.d(TAG, "Received location result: $locationResult")
                val location = locationResult.lastLocation
                if (location != null) {
                    processLocation(context, location, onResult)
                    fusedLocationClient.removeLocationUpdates(this)
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        ).addOnFailureListener { e ->
            handler.removeCallbacks(timeoutRunnable)
            Log.e(TAG, "Location updates failed", e)
            handler.post { onResult("Sijaintia ei saatavilla") }
        }
    }

    private fun processLocation(
        context: Context,
        location: android.location.Location,
        onResult: (String) -> Unit
    ) {
        Log.d(TAG, "Processing location: lat=${location.latitude}, lon=${location.longitude}")

        val geocoder = Geocoder(context, Locale.getDefault())
        val handler = Handler(Looper.getMainLooper())

        try {
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val city = addresses[0].locality ?: ""
                val country = addresses[0].countryName ?: ""
                val resolved = "$city, $country".takeIf { it.isNotBlank() } ?: "Sijaintia ei löytynyt"
                Log.d(TAG, "Resolved address: $resolved")
                handler.post { onResult(resolved) }
            } else {
                Log.d(TAG, "No address found from geocoder.")
                handler.post { onResult("Sijaintia ei löytynyt") }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Geocoding error", e)
            handler.post { onResult("Sijaintitietoja ei voitu käsitellä") }
        }
    }
}