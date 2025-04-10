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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.hardwarecheck.navigation.AppNavHost
import com.example.hardwarecheck.navigation.BottomNavigationBar
import com.example.hardwarecheck.navigation.isGuideScreen
import com.example.hardwarecheck.ui.theme.HardwareCheckTheme

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