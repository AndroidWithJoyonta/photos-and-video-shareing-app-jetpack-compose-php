package com.sharing.monio.presentation.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Light Color Scheme
private val LightColorScheme = lightColorScheme(
    primary = blue,
    onPrimary = black,
    background = black,
    onBackground = white,
    surface = Color(0xFF1C1C1E), // a soft dark gray
    onSurface = white,
    secondary = PurpleGrey40,
    onSecondary = white
)

// Dark Color Scheme
private val DarkColorScheme = darkColorScheme(
    primary = blue,
    onPrimary = white,
    background = gray,
    onBackground = black,
    surface = white,
    onSurface = black,
    secondary = PurpleGrey80,
    onSecondary = black,
)


@Composable
fun MonioTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean = true,
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
