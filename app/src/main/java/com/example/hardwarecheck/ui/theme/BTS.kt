package com.example.hardwarecheck.ui.theme

//PURPLE THEME.KT

/*
package com.example.hardwarecheck.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = CyberPurplePrimary,
    secondary = CyberPurpleSecondary,
    tertiary = CyberPurpleTertiary,
    background = CyberPurpleBackgroundDark,
    surface = CyberPurpleSurfaceDark,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = CyberPurplePrimary,
    secondary = CyberPurpleSecondary,
    tertiary = CyberPurpleTertiary,
    background = CyberPurpleBackgroundLight,
    surface = CyberPurpleSurfaceLight,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
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

PURPLE COLOR.KT

package com.example.hardwarecheck.ui.theme

import androidx.compose.ui.graphics.Color

val CyberPurplePrimary = Color(0xFF7C4DFF)
val CyberPurpleSecondary = Color(0xFFB388FF)
val CyberPurpleTertiary = Color(0xFF651FFF)

val CyberPurpleBackgroundLight = Color(0xFFF4F3FF)
val CyberPurpleSurfaceLight = Color(0xFFFFFFFF)

val CyberPurpleBackgroundDark = Color(0xFF121212)
val CyberPurpleSurfaceDark = Color(0xFF1E1E1E)

*/

/* GREY BLUE THEME COLOR.KT
package com.example.hardwarecheck.ui.theme

import androidx.compose.ui.graphics.Color

val IndustrialPrimary = Color(0xFF37474F)
val IndustrialSecondary = Color(0xFF90A4AE)
val IndustrialAccent = Color(0xFF00BCD4)

val IndustrialBackgroundLight = Color(0xFFFAFAFA)
val IndustrialSurfaceLight = Color(0xFFFFFFFF)

val IndustrialBackgroundDark = Color(0xFF121212)
val IndustrialSurfaceDark = Color(0xFF1E1E1E)


GREY BLUE THEME THEME.KT

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

 */

/* YELLOW THEMES.KT
package com.example.hardwarecheck.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

private val AmberTechDarkColorScheme = darkColorScheme(
    primary = AmberTechPrimary,
    secondary = AmberTechSecondary,
    tertiary = AmberTechAccent,
    background = AmberTechBackgroundDark,
    surface = AmberTechSurfaceDark,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    primaryContainer = AmberTechPrimaryContainer,
    onPrimaryContainer = AmberTechOnPrimaryContainer
)

private val AmberTechLightColorScheme = lightColorScheme(
    primary = AmberTechPrimary,
    secondary = AmberTechSecondary,
    tertiary = AmberTechAccent,
    background = AmberTechBackgroundLight,
    surface = AmberTechSurfaceLight,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    primaryContainer = AmberTechPrimaryContainer,
    onPrimaryContainer = AmberTechOnPrimaryContainer
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
        darkTheme -> AmberTechDarkColorScheme
        else -> AmberTechLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}


YELLOW THEME COLOR.KT

package com.example.hardwarecheck.ui.theme

import androidx.compose.ui.graphics.Color

val AmberTechPrimary = Color(0xFFFFB300)
val AmberTechSecondary = Color(0xFFFFD54F)
val AmberTechAccent = Color(0xFFFFA000)

val AmberTechBackgroundLight = Color(0xFFFFFDF5)
val AmberTechSurfaceLight = Color(0xFFFFFFFF)

val AmberTechBackgroundDark = Color(0xFF121212)
val AmberTechSurfaceDark = Color(0xFF1E1E1E)

val AmberTechPrimaryContainer = AmberTechPrimary
val AmberTechOnPrimaryContainer = Color.Black
val AmberTechOnPrimary = Color.White
val AmberTechOnSurface = Color.Black

 */

/*

GREEN THEME COLOR.KT

package com.example.hardwarecheck.ui.theme

import androidx.compose.ui.graphics.Color

val TechGreenPrimary = Color(0xFF00C853)
val TechGreenSecondary = Color(0xFF69F0AE)
val TechGreenAccent = Color(0xFF00E676)

val TechGreenBackgroundLight = Color(0xFFF1F8F5)
val TechGreenSurfaceLight = Color(0xFFFFFFFF)

val TechGreenBackgroundDark = Color(0xFF121212)
val TechGreenSurfaceDark = Color(0xFF1E1E1E)

val TechGreenPrimaryContainer = TechGreenPrimary
val TechGreenOnPrimaryContainer = Color.Black
val TechGreenOnPrimary = Color.White
val TechGreenOnSurface = Color.Black


GREEN THEMES.KT

package com.example.hardwarecheck.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color



private val TechGreenDarkColorScheme = darkColorScheme(
    primary = TechGreenPrimary,
    secondary = TechGreenSecondary,
    tertiary = TechGreenAccent,
    background = TechGreenBackgroundDark,
    surface = TechGreenSurfaceDark,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    primaryContainer = TechGreenPrimaryContainer,
    onPrimaryContainer = TechGreenOnPrimaryContainer
)

private val TechGreenLightColorScheme = lightColorScheme(
    primary = TechGreenPrimary,
    secondary = TechGreenSecondary,
    tertiary = TechGreenAccent,
    background = TechGreenBackgroundLight,
    surface = TechGreenSurfaceLight,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    primaryContainer = TechGreenPrimaryContainer,
    onPrimaryContainer = TechGreenOnPrimaryContainer
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
        darkTheme -> TechGreenDarkColorScheme
        else -> TechGreenLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

 */