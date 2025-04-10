package com.example.hardwarecheck.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hardwarecheck.viewmodel.ThemeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun OverviewScreen(
    navController: NavController,
    themeViewModel: ThemeViewModel = viewModel()
) {
    val isDarkTheme by themeViewModel.isDarkTheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Overview",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            TutorialTopIcon(onHelpClick = {
                navController.navigate("guide")
            })
        }

        Spacer(modifier = Modifier.weight(1f))

        // Theme Switcher near bottom
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (isDarkTheme) "Dark Mode" else "Light Mode",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { themeViewModel.toggleTheme() },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.tertiary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.surfaceVariant,
                    checkedTrackColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                    uncheckedTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
            )


        }
    }
}
