package com.example.hardwarecheck.model
import org.json.JSONObject

import java.net.HttpURLConnection
import java.net.URL
import com.google.gson.JsonParser

fun getLocation(): String {
    return try {
        val url = URL("https://ipapi.co/json/")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        val response = connection.inputStream.bufferedReader().use { it.readText() }
        response // Tämä sisältää JSON-muotoisen sijaintitiedon
    } catch (e: Exception) {
        "Sijainnin hakeminen epäonnistui: ${e.message}"
    }
}




fun getCityAndCountry(): String {
    val jsonStr = getLocation()
    val json = JsonParser.parseString(jsonStr).asJsonObject
    val city = json["city"].asString
    val country = json["country_name"].asString
    return "Sijaintisi: $city, $country"
}


fun main() {
    println(getCityAndCountry())
}
