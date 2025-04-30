package com.example.hardwarecheck

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.example.hardwarecheck.database.FirestoreManager
import com.example.hardwarecheck.navigation.AppNavHost
import com.example.hardwarecheck.navigation.BottomNavigationBar
import com.example.hardwarecheck.navigation.isGuideScreen
import com.example.hardwarecheck.ui.theme.HardwareCheckTheme
import com.example.hardwarecheck.utils.HardwareInfoUtils
import com.example.hardwarecheck.utils.LocationUtil
import com.example.hardwarecheck.utils.PreferenceHelper
import com.example.hardwarecheck.utils.getCityAndCountryFromIP
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        val savedLanguage = PreferenceHelper.getAppLanguage(this)
        PreferenceHelper.applyLocale(this, savedLanguage)

        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        val firestoreManager = FirestoreManager()
        val deviceInfo = HardwareInfoUtils.collectDeviceInfo(this)
        val deviceId = android.provider.Settings.Secure.getString(
            contentResolver,
            android.provider.Settings.Secure.ANDROID_ID
        )

        if (PreferenceHelper.isSaveDataEnabled(this)) {
            firestoreManager.saveDeviceInfo(context = this, deviceId, deviceInfo)
        }

        if (LocationUtil.savedLocation == null) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                LocationUtil.getCityAndCountry(this) { result ->
                    LocationUtil.savedLocation = result
                }
            } else {
                LocationUtil.savedLocation = getCityAndCountryFromIP()
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

    override fun attachBaseContext(newBase: Context) {
        // Apply language to base context
        super.attachBaseContext(
            ContextWrapper(
                newBase.apply {
                    PreferenceHelper.applyLocale(this, PreferenceHelper.getAppLanguage(this))
                }
            )
        )
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            if (!isGuideScreen(navController)) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HardwareCheckTheme {
        MainApp()
    }
}
