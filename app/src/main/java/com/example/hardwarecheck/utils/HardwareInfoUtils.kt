package com.example.hardwarecheck.utils

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.*
import com.example.hardwarecheck.model.DeviceInfo
import java.io.File
import java.util.*

object HardwareInfoUtils {

    @SuppressLint("HardwareIds")
    fun collectDeviceInfo(context: Context): DeviceInfo {
        return DeviceInfo(
            model = getDeviceModel(),
            osInfo = getAndroidVersion(),
            processor = getProcessorInfo(),
            memory = getTotalRAM(context),
            cores = getCoresCount(),
            storage = getStorageInfo(),
            gpu = getGpuInfo(),
            sensors = getSensorsCount(context)
        )
    }

    private fun getDeviceModel(): String {
        return "${Build.MANUFACTURER.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }} ${Build.MODEL} (${Build.DEVICE})"
    }

    private fun getAndroidVersion(): String {
        return "Android ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})\n" +
                "Security Patch: ${Build.VERSION.SECURITY_PATCH}\n" +
                "Kernel: ${System.getProperty("os.version") ?: "Unknown"}"
    }

    private fun getProcessorInfo(): String {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                Build.SOC_MODEL?.let { socModel ->
                    "$socModel\n${Build.HARDWARE} (${Build.SUPPORTED_ABIS.joinToString()})"
                } ?: "${Build.HARDWARE} (${Build.SUPPORTED_ABIS.joinToString()})"
            }
            else -> {
                "${Build.HARDWARE}\n${Build.SUPPORTED_ABIS.joinToString()}"
            }
        } + "\nArch: ${System.getProperty("os.arch") ?: "Unknown"}"
    }

    @SuppressLint("NewApi")
    private fun getTotalRAM(context: Context): String {
        return try {
            val memInfo = ActivityManager.MemoryInfo()
            (context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).getMemoryInfo(memInfo)
            val totalMemoryGB = memInfo.totalMem / (1024.0 * 1024 * 1024)
            "%.1f GB\n(Physical: ${getPhysicalMemory(context)})".format(totalMemoryGB)
        } catch (e: Exception) {
            "RAM Unavailable"
        }
    }

    @SuppressLint("NewApi")
    private fun getPhysicalMemory(context: Context): String {
        return try {
                val memInfo = ActivityManager.MemoryInfo()
                (context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).getMemoryInfo(memInfo)
                "${(memInfo.totalMem / (1024 * 1024 * 1024))} GB"
        } catch (e: Exception) {
            "Unknown"
        }
    }

    private fun getCoresCount(): String {
        return try {
            Runtime.getRuntime().availableProcessors().toString()
        } catch (e: Exception) {
            "Unknown"
        }
    }

    private fun getStorageInfo(): String {
        return try {
            val internal = getStorageSize(Environment.getDataDirectory())
            val external = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                "\nExternal: ${getStorageSize(Environment.getExternalStorageDirectory())}"
            } else ""
            "$internal$external"
        } catch (e: Exception) {
            "Storage Unavailable"
        }
    }

    private fun getStorageSize(directory: File): String {
        val stat = StatFs(directory.path)
        val totalBytes = stat.blockSizeLong * stat.blockCountLong
        return "%.1f GB".format(totalBytes / (1024.0 * 1024 * 1024))
    }

    private fun getGpuInfo(): String {
        return try {
            if (isEmulator()) {
                "Running on Emulator: GPU info may not be accurate"
            } else {
                val glRenderer =
                    android.opengl.GLES20.glGetString(android.opengl.GLES20.GL_RENDERER)
                        ?: "Unknown Renderer"
                val glVendor = android.opengl.GLES20.glGetString(android.opengl.GLES20.GL_VENDOR)
                    ?: "Unknown Vendor"
                val glVersion = android.opengl.GLES20.glGetString(android.opengl.GLES20.GL_VERSION)
                    ?: "Unknown Version"

                "GPU Renderer: $glRenderer\nGPU Vendor: $glVendor\nOpenGL Version: $glVersion"
            }
        } catch (e: Exception) {
            "GPU Unavailable: ${e.localizedMessage}"
        }
    }

    private fun isEmulator(): Boolean {
        return Build.FINGERPRINT.contains("generic") || Build.MODEL.contains("Emulator") ||
                Build.MANUFACTURER.contains("Genymotion") || Build.HARDWARE.contains("goldfish") ||
                Build.HARDWARE.contains("ranchu") || Build.BRAND.contains("google")
    }



    private fun getSensorsCount(context: Context): String {
        return try {
            val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            val sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
            "${sensors.size} sensors\n" +
                    sensors.distinctBy { it.type }.joinToString {
                        when (it.type) {
                            Sensor.TYPE_ACCELEROMETER -> "Accel"
                            Sensor.TYPE_GYROSCOPE -> "Gyro"
                            Sensor.TYPE_LIGHT -> "Light"
                            Sensor.TYPE_PROXIMITY -> "Proximity"
                            Sensor.TYPE_MAGNETIC_FIELD -> "Mag"
                            else -> it.name
                        }
                    }.take(50) + "..."
        } catch (e: Exception) {
            "Sensors Unavailable"
        }
    }
}