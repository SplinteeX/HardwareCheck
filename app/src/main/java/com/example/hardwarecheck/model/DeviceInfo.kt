package com.example.hardwarecheck.model

data class DeviceInfo(
    val model: String,
    val osInfo: String,
    val processor: String,
    val memory: String,
    val cores: String,
    val storage: String,
    val gpu: String,
    val sensors: String
)