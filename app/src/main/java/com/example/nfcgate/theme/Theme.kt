package com.example.nfcgate.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = BackgroundColor,
    primaryContainer = PrimaryTint,
    onPrimaryContainer = BackgroundColor,
    secondary = PrimaryColor,
    onSecondary = BackgroundColor,
    background = BackgroundColor,
    onBackground = TextPrimary,
    surface = BackgroundColor,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceColor,
    onSurfaceVariant = TextSecondary,
    outline = Color.Transparent,
    error = ErrorColor,
    onError = BackgroundColor
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor,
    onPrimary = BackgroundColor,
    primaryContainer = PrimaryColor,
    onPrimaryContainer = BackgroundColor,
    secondary = PrimaryColor,
    onSecondary = BackgroundColor,
    background = TextPrimary, // Inverting for simplicity, normally a dark theme has specific dark colors
    onBackground = BackgroundColor,
    surface = TextPrimary,
    onSurface = BackgroundColor,
    surfaceVariant = SurfaceVariantColor,
    onSurfaceVariant = TextHint,
    outline = OutlineColor,
    error = ErrorColor,
    onError = BackgroundColor
)

@Composable
fun NFCGateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
