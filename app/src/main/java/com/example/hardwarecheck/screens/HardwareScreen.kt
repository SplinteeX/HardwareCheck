package com.example.hardwarecheck.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hardwarecheck.model.DeviceInfo
import com.example.hardwarecheck.R
import androidx.navigation.NavController

@Composable
fun HardwareScreen(deviceInfo: DeviceInfo, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
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
                text = "Hardware Specifications",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            TutorialTopIcon(onHelpClick = {
                navController.navigate("guide")
            })
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

        hardwareInfoItems.forEach { (iconRes, title, value) ->
            InfoItemWithIcon(
                icon = painterResource(iconRes),
                title = title,
                value = value
            )
        }
    }
}

@Composable
fun InfoItemWithIcon(icon: Painter, title: String, value: String) {
    Column(modifier = Modifier.padding(bottom = 20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = Color.Blue,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = value,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 36.dp)
        )
    }
}
