package com.example.hardwarecheck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hardwarecheck.navigation.BottomNavigationBar
import com.example.hardwarecheck.navigation.Screen
import com.example.hardwarecheck.screens.HardwareScreen
import com.example.hardwarecheck.screens.OverviewScreen
import com.example.hardwarecheck.screens.ProfileScreen
import com.example.hardwarecheck.screens.GuideScreen
import com.example.hardwarecheck.ui.theme.HardwareCheckTheme
import com.example.hardwarecheck.utils.HardwareInfoUtils
import com.example.hardwarecheck.utils.PreferenceHelper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    val context = LocalContext.current
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isGuideScreen = currentDestination?.route == "guide"

    val shouldShowGuide = remember {
        PreferenceHelper.shouldShowGuide(context)
    }

    LaunchedEffect(Unit) {
        if (shouldShowGuide) {
            navController.navigate("guide")
            PreferenceHelper.setGuideShown(context, true)
        }
    }

    Scaffold(
        bottomBar = {
            if (!isGuideScreen) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Hardware.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Hardware.route) {
                HardwareScreen(
                    deviceInfo = HardwareInfoUtils.collectDeviceInfo(context),
                    navController = navController
                )
            }
            composable(Screen.Overview.route) {
                OverviewScreen(navController = navController)
            }
            composable(Screen.Profile.route) {
                ProfileScreen(navController = navController)
            }
            composable("guide") {
                GuideScreen(onFinish = {
                    navController.popBackStack()
                })
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