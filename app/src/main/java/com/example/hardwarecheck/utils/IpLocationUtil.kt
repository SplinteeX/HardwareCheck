package com.example.hardwarecheck.utils

import com.google.gson.JsonParser
import java.net.HttpURLConnection
import java.net.URL

fun getCityAndCountryFromIP(): String {
    return try {
        val url = URL("https://ipapi.co/json/")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        val response = connection.inputStream.bufferedReader().use { it.readText() }

        val json = JsonParser.parseString(response).asJsonObject
        val city = json["city"].asString
        val country = json["country_name"].asString

        "$city, $country"
    } catch (e: Exception) {
        "IP-sijaintia ei voitu hakea: ${e.message}"
    }
}
