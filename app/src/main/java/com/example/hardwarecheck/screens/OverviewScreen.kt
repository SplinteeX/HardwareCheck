package com.example.hardwarecheck.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hardwarecheck.utils.LocationUtil

@Composable
fun OverviewScreen(navController: NavController) {
    val location by remember { derivedStateOf { LocationUtil.savedLocation ?: "Haetaan sijaintia..." } }
    val context = LocalContext.current

    // Start fetching location if not set yet
    LaunchedEffect(Unit) {
        if (LocationUtil.savedLocation == null) {
            LocationUtil.getCityAndCountry(context)
        }
    }

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
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 20.dp)
            )

            IconButton(onClick = { navController.navigate("guide") }) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Opas"
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Sijainti", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = location)
            }
        }

        // Lisää muita kortteja tähän
    }
}

