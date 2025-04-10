package com.example.hardwarecheck

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hardwarecheck.database.FirestoreManager
import com.example.hardwarecheck.navigation.BottomNavigationBar
import com.example.hardwarecheck.navigation.Screen
import com.example.hardwarecheck.screens.HardwareScreen
import com.example.hardwarecheck.screens.OverviewScreen
import com.example.hardwarecheck.screens.ProfileScreen
import com.example.hardwarecheck.ui.theme.HardwareCheckTheme
import com.example.hardwarecheck.utils.HardwareInfoUtils
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        val firestoreManager = FirestoreManager()

        val deviceInfo = HardwareInfoUtils.collectDeviceInfo(this)
        val deviceId = android.provider.Settings.Secure.getString(contentResolver, android.provider.Settings.Secure.ANDROID_ID)

        firestoreManager.saveDeviceInfo(deviceId, deviceInfo)

        firestoreManager.getDeviceInfo(deviceId) { retrievedDeviceInfo ->
            if (retrievedDeviceInfo != null) {
                Log.d("FirestoreTest", "Retrieved Device Info: $retrievedDeviceInfo")
            } else {
                Log.d("FirestoreTest", "Failed to retrieve device info")
            }
        }

        setContent {
            HardwareCheckTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp()
                }
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val context = LocalContext.current

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Hardware.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Hardware.route) {
                HardwareScreen(HardwareInfoUtils.collectDeviceInfo(context))
            }
            composable(Screen.Overview.route) {
                OverviewScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HardwareCheckTheme {
        MainApp()
    }
}