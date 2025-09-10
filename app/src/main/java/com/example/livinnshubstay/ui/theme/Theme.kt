package com.example.livinnshubstay.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Custom dark theme color scheme based on the specified design
private val LivinnsHubStayColorScheme = darkColorScheme(
    primary = VibrantPurple,
    secondary = NeonGreen,
    tertiary = SoftViolet,
    background = DeepDarkBackground,
    surface = DarkGrey,
    onPrimary = TextWhite,
    onSecondary = TextWhite,
    onTertiary = TextWhite,
    onBackground = TextWhite,
    onSurface = TextWhite,
    surfaceVariant = MediumGrey,
    onSurfaceVariant = TextGrey
)

@Composable
fun LivinnsHubStayTheme(
    darkTheme: Boolean = true, // Always use dark theme as per design
    // Dynamic color is disabled to use our custom theme
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = LivinnsHubStayColorScheme
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = DeepDarkBackground.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}