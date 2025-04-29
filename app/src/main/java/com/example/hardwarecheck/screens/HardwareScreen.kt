package com.example.hardwarecheck.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hardwarecheck.R
import com.example.hardwarecheck.model.DeviceInfo
import androidx.navigation.NavController

@Composable
fun HardwareScreen(deviceInfo: DeviceInfo, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Hardware Specifications",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            IconButton(onClick = { navController.navigate("guide") }) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Guide",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        val hardwareInfoItems = listOf(
            Triple(R.drawable.os, "Device Model", deviceInfo.model),
            Triple(R.drawable.os, "Operating System", deviceInfo.osInfo),
            Triple(R.drawable.memory, "Processor", deviceInfo.processor),
            Triple(R.drawable.memory, "CPU Cores", deviceInfo.cores),
            Triple(R.drawable.memory, "Memory (RAM)", deviceInfo.memory),
            Triple(R.drawable.display, "Screen", deviceInfo.screen),
            Triple(R.drawable.storage, "Storage", deviceInfo.storage),
            Triple(R.drawable.gpu, "GPU", deviceInfo.gpu),
            Triple(R.drawable.sensors, "Sensors", deviceInfo.sensors),
            Triple(R.drawable.battery, "Battery", deviceInfo.battery),
            Triple(R.drawable.progress, "Uptime", deviceInfo.uptime),
            Triple(R.drawable.baseband, "Baseband", deviceInfo.baseband),
            Triple(R.drawable.build, "Build Date", deviceInfo.buildDate),
            Triple(R.drawable.wifi, "Wi-Fi Version", deviceInfo.wifiVersion),
            Triple(R.drawable.bluetooth, "Bluetooth Version", deviceInfo.bluetoothVersion),
        )

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            hardwareInfoItems.forEach { (iconRes, title, value) ->
                InfoItemCard(
                    icon = painterResource(iconRes),
                    title = title,
                    value = value
                )
            }
        }
    }
}


@Composable
fun InfoItemCard(icon: Painter, title: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = title, fontWeight = FontWeight.SemiBold)
                Text(text = value, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
