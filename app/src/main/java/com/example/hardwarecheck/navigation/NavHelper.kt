package com.example.hardwarecheck.navigation

import ProfileScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.hardwarecheck.screens.GuideScreen
import com.example.hardwarecheck.screens.HardwareScreen
import com.example.hardwarecheck.screens.OverviewScreen
import com.example.hardwarecheck.utils.HardwareInfoUtils
import com.example.hardwarecheck.utils.PreferenceHelper

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val shouldShowGuide = remember { PreferenceHelper.shouldShowGuide(context) }

    LaunchedEffect(Unit) {
        if (shouldShowGuide) {
            navController.navigate("guide")
            PreferenceHelper.setGuideShown(context, true)
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Hardware.route,
        modifier = modifier
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
            ProfileScreen()
        }
        composable("guide") {
            GuideScreen(onFinish = {
                navController.popBackStack()
            })
        }
    }
}

@Composable
fun isGuideScreen(navController: NavHostController): Boolean {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route == "guide"
}
