package com.example.hardwarecheck.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hardwarecheck.database.FirestoreManager
import com.example.hardwarecheck.model.DeviceInfo
import androidx.compose.ui.res.stringResource
import com.example.hardwarecheck.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecentDevicesScreen(navController: NavController) {
    val firestoreManager = remember { FirestoreManager() }
    var devices by remember { mutableStateOf<List<DeviceInfo>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        firestoreManager.getLatestDevices { result ->
            devices = result
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.recent_devices)) }, // Updated with string resource
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.guide_back) // Updated with string resource
                        )
                    }
                }
            )
        }
    ) { padding ->
        if (isLoading) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(padding),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(devices) { device ->
                    DeviceCard(device)
                }
            }
        }
    }
}

@Composable
fun DeviceCard(device: DeviceInfo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("${stringResource(id = R.string.device_model)}: ${device.model}", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(8.dp))
            Text("${stringResource(id = R.string.processor)}: ${device.processor} (${device.cores} ${stringResource(id = R.string.cpu_cores)})", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(4.dp))
            Text("${stringResource(id = R.string.gpu)}: ${device.gpu}", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(4.dp))
            Text("${stringResource(id = R.string.memory)}: ${device.memory}", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(4.dp))
            Text("${stringResource(id = R.string.storage)}: ${device.storage}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
