package com.example.hardwarecheck.database

import android.content.Context
import android.util.Log
import com.example.hardwarecheck.model.DeviceInfo
import com.example.hardwarecheck.utils.PreferenceHelper
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreManager {
    private val db = FirebaseFirestore.getInstance()

    // Save DeviceInfo to Firestore
    fun saveDeviceInfo(context: Context, deviceId: String, deviceInfo: DeviceInfo) {
        if (!PreferenceHelper.isSaveDataEnabled(context)) {
            Log.d("Firestore", "Data saving is disabled by user preference")
            return
        }

        val deviceInfoMap = mapOf(
            "model" to deviceInfo.model,
            "osInfo" to deviceInfo.osInfo,
            "processor" to deviceInfo.processor,
            "memory" to deviceInfo.memory,
            "cores" to deviceInfo.cores,
            "storage" to deviceInfo.storage,
            "gpu" to deviceInfo.gpu,
            "sensors" to deviceInfo.sensors,
            "screen" to deviceInfo.screen,
            "battery" to deviceInfo.battery,
            "uptime" to deviceInfo.uptime,
            "baseband" to deviceInfo.baseband,
            "buildDate" to deviceInfo.buildDate,
            "wifiVersion" to deviceInfo.wifiVersion,
            "bluetoothVersion" to deviceInfo.bluetoothVersion
        )

        db.collection("devices").document(deviceId)
            .set(deviceInfoMap)
            .addOnSuccessListener { Log.d("Firestore", "Device info saved successfully") }
            .addOnFailureListener { e -> Log.w("Firestore", "Error saving device info", e) }
    }

    // Retrieve DeviceInfo from Firestore
    fun getDeviceInfo(deviceId: String, callback: (DeviceInfo?) -> Unit) {
        db.collection("devices").document(deviceId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val deviceInfo = document.toObject(DeviceInfo::class.java)
                    callback(deviceInfo)
                } else {
                    Log.d("Firestore", "No such document")
                    callback(null)
                }
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error getting device info", e)
                callback(null)
            }
    }
    fun deleteDeviceInfo(deviceId: String) {
        db.collection("devices").document(deviceId)
            .delete()
            .addOnSuccessListener { Log.d("Firestore", "Device info deleted successfully") }
            .addOnFailureListener { e -> Log.w("Firestore", "Error deleting device info", e) }
    }

}
