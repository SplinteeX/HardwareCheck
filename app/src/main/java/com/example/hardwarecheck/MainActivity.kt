package com.example.hardwarecheck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hardwarecheck.navigation.BottomNavigationBar
import com.example.hardwarecheck.navigation.Screen
import com.example.hardwarecheck.screens.GuideScreen
import com.example.hardwarecheck.screens.HardwareScreen
import com.example.hardwarecheck.screens.OverviewScreen
import com.example.hardwarecheck.screens.ProfileScreen
import com.example.hardwarecheck.ui.theme.HardwareCheckTheme
import com.example.hardwarecheck.utils.HardwareInfoUtils
import com.example.hardwarecheck.viewmodel.ThemeViewModel

class MainActivity : ComponentActivity() {
    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkTheme = themeViewModel.isDarkTheme.value
            HardwareCheckTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp(themeViewModel)
                }
            }
        }
    }
}

@Composable
fun MainApp(themeViewModel: ThemeViewModel) {
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
                HardwareScreen(HardwareInfoUtils.collectDeviceInfo(context), navController)
            }
            composable(Screen.Overview.route) {
                OverviewScreen(navController,themeViewModel = themeViewModel)
            }
            composable(Screen.Profile.route) {
                ProfileScreen(navController)
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
    val fakeNavController = rememberNavController()

    // Suppress warning for preview ONLY
    val fakeThemeViewModel = remember { ThemeViewModel() }

    HardwareCheckTheme {
        OverviewScreen(navController = fakeNavController, themeViewModel = fakeThemeViewModel)
    }
}


