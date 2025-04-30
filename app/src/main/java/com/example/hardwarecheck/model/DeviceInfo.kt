package com.example.hardwarecheck.model

data class DeviceInfo(
    val model: String = "",
    val osInfo: String = "",
    val processor: String = "",
    val memory: String = "",
    val cores: String = "",
    val storage: String = "",
    val gpu: String = "",
    val sensors: String = "",
    val screen: String = "",
    val battery: String = "",
    val uptime: String = "",
    val baseband: String = "",
    val buildDate: String = "",
    val wifiVersion: String = "",
    val bluetoothVersion: String = "",
    var timestamp: com.google.firebase.Timestamp? = null
)
