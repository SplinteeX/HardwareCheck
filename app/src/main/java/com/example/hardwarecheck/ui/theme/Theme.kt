package com.example.hardwarecheck.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = IndustrialPrimary,
    secondary = IndustrialSecondary,
    tertiary = IndustrialAccent,
    background = IndustrialBackgroundDark,
    surface = IndustrialSurfaceDark,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    primaryContainer = IndustrialPrimary,
    onPrimaryContainer = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = IndustrialPrimary,
    secondary = IndustrialSecondary,
    tertiary = IndustrialAccent,
    background = IndustrialBackgroundLight,
    surface = IndustrialSurfaceLight,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    primaryContainer = IndustrialPrimary,
    onPrimaryContainer = Color.White
)


@Composable
fun HardwareCheckTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}