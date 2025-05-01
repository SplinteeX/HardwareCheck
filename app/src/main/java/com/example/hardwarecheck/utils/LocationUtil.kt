package com.example.hardwarecheck.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.google.android.gms.location.LocationServices
import java.util.*

object LocationUtil {
    var savedLocation by mutableStateOf<String?>(null)

    @SuppressLint("MissingPermission")
    fun getCityAndCountry(context: Context, onResult: (String) -> Unit = {}) {
        savedLocation?.let {
            onResult(it)
            return
        }

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        val locationRequest = com.google.android.gms.location.LocationRequest.Builder(
            com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY, 1000L
        ).build()
        // Location
        val locationCallback = object : com.google.android.gms.location.LocationCallback() {
            override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                val location = locationResult.lastLocation
                if (location != null) {
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    if (!addresses.isNullOrEmpty()) {
                        val address = addresses[0]
                        val city = address.locality ?: ""
                        val country = address.countryName ?: ""
                        savedLocation = "$city, $country"
                        onResult(savedLocation!!)
                    } else {
                        savedLocation = "Sijaintia ei löytynyt"
                        onResult(savedLocation!!)
                    }
                } else {
                    savedLocation = "Sijaintia ei saatavilla"
                    onResult(savedLocation!!)
                }
                fusedLocationClient.removeLocationUpdates(this)
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }
}
