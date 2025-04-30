package com.example.hardwarecheck.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hardwarecheck.utils.LocationUtil
import com.example.hardwarecheck.R
import androidx.compose.ui.res.stringResource

@Composable
fun OverviewScreen(navController: NavController) {
    val location = LocationUtil.savedLocation ?: stringResource(id = R.string.fetching_location)
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (LocationUtil.savedLocation == null) {
            LocationUtil.getCityAndCountry(context)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.overview),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            IconButton(onClick = { navController.navigate("guide") }) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Opas",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        LocationCard(location)

        Spacer(modifier = Modifier.height(16.dp))

        FeatureCard(
            title = stringResource(id = R.string.last_5_devices),
            description = stringResource(id = R.string.recent_devices),
            icon = Icons.Default.Person,
            onClick = { navController.navigate("recent_devices") }
        )

        FeatureCard(
            title = stringResource(id = R.string.common_specs),
            description = stringResource(id = R.string.reported_specs),
            icon = Icons.Default.Person,
            onClick = { navController.navigate("common_specs") }
        )

        FeatureCard(
            title = stringResource(id = R.string.app_usage),
            description = stringResource(id = R.string.top_countries),
            icon = Icons.Default.Person,
            onClick = { navController.navigate("top_countries") }
        )
    }
}

@Composable
fun LocationCard(location: String) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(stringResource(id = R.string.location), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = location, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun FeatureCard(title: String, description: String, icon: ImageVector, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp, pressedElevation = 8.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 16.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(description, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.read_more),
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary)
                )
            }
        }
    }
}
