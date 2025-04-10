package com.example.hardwarecheck.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.hardwarecheck.R

sealed class Screen(val route: String) {
    object Hardware : Screen("hardware")
    object Overview : Screen("overview")
    object Profile : Screen("profile")
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(Screen.Hardware, Screen.Overview, Screen.Profile)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val colorScheme = MaterialTheme.colorScheme

    NavigationBar {
        items.forEach { screen ->
            val icon = when (screen) {
                Screen.Hardware -> Icons.Filled.Settings
                Screen.Overview -> Icons.Filled.Info
                Screen.Profile -> Icons.Filled.Edit
            }
            val label = stringResource(when (screen) {
                Screen.Hardware -> R.string.hardware
                Screen.Overview -> R.string.overview
                Screen.Profile -> R.string.profile
            })

            val selected = currentRoute == screen.route

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (selected) colorScheme.onTertiary else colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                },
                label = { Text(label) },
                selected = selected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = colorScheme.tertiary
                )
            )
        }
    }
}
