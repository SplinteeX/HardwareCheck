package com.example.hardwarecheck.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
// --
@Composable
fun HardwareScreen(deviceInfo: DeviceInfo) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Hardware Specifications",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 20.dp)
        )

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