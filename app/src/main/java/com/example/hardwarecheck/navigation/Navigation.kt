package com.example.hardwarecheck.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.hardwarecheck.R

sealed class Screen(val route: String) {
    object Hardware : Screen("hardware")
    object Overview : Screen("overview")
    object Profile : Screen("profile")
    object Camera : Screen("camera")
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(Screen.Hardware, Screen.Overview, Screen.Camera, Screen.Profile)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        tonalElevation = 4.dp,
        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
    ) {
        items.forEach { screen ->
            val isSelected = currentRoute == screen.route

            val icon = when (screen) {
                Screen.Hardware -> Icons.Filled.Settings
                Screen.Overview -> Icons.Filled.Info
                Screen.Profile -> Icons.Filled.Edit
                Screen.Camera -> Icons.Filled.Face
            }

            val label = stringResource(when (screen) {
                Screen.Hardware -> R.string.hardware
                Screen.Overview -> R.string.overview
                Screen.Profile -> R.string.profile
                Screen.Camera -> R.string.camera
            })

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                label = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                selected = isSelected,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}
