package com.example.hardwarecheck.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            TutorialTopIcon(onHelpClick = {
                navController.navigate("guide")
            })
        }

        listOf(
            Triple(R.drawable.os, "Device Model", deviceInfo.model),
            Triple(R.drawable.os, "Operating System", deviceInfo.osInfo),
            Triple(R.drawable.memory, "Processor", deviceInfo.processor),
            Triple(R.drawable.memory, "CPU Cores", deviceInfo.cores),
            Triple(R.drawable.memory, "Memory (RAM)", deviceInfo.memory),
            Triple(R.drawable.storage, "Storage", deviceInfo.storage),
            Triple(R.drawable.gpu, "GPU", deviceInfo.gpu),
            Triple(R.drawable.sensors, "Sensors", deviceInfo.sensors)
        ).forEach { (iconRes, title, value) ->
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
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Text(
            text = value,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 36.dp),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
