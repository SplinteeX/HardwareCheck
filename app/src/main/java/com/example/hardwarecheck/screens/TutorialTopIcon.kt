package com.example.hardwarecheck.screens

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.hardwarecheck.R
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon



@Composable
fun TutorialTopIcon(onHelpClick: () -> Unit) {
    IconButton(onClick = onHelpClick) {
        Icon(
            painter = painterResource(R.drawable.baseline_help_outline_24),
            contentDescription = "Guide",
            tint = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.size(28.dp)
        )
    }
}
