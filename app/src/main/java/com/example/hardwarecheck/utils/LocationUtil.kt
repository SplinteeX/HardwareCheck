package com.example.hardwarecheck.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.LocationServices
import java.util.*

object LocationUtil {

    @SuppressLint("MissingPermission")
    fun getCityAndCountry(context: Context, onResult: (String) -> Unit) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    if (!addresses.isNullOrEmpty()) {
                        val address = addresses[0]
                        val city = address.locality ?: ""
                        val country = address.countryName ?: ""
                        onResult("$city, $country")
                    } else {
                        onResult("Sijaintia ei löytynyt")
                    }
                } else {
                    onResult("Sijaintia ei saatavilla")
                }
            }
            .addOnFailureListener {
                onResult("Sijainnin haku epäonnistui")
            }
    }
}
