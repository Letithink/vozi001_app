package com.example.vozi001.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.ui.graphics.Color

// Imports de tus colores (¡Asegúrate de que existan!)
import com.example.vozi001.ui.theme.VoziBlue
import com.example.vozi001.ui.theme.VoziWhite
import com.example.vozi001.ui.theme.VoziBlack
import com.example.vozi001.ui.theme.Typography

// Tu LightColorScheme ya debería estar así:
private val LightColorScheme = lightColorScheme(
    primary = VoziBlue,
    onPrimary = VoziWhite,
    background = VoziWhite,
    onBackground = VoziBlack,
    surface = VoziWhite,
    onSurface = VoziBlack
    // ... (el resto de tus colores)
)

// (Tu DarkColorScheme puede quedar como estaba)
private val DarkColorScheme = darkColorScheme(
    primary = VoziBlue,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = Color.Black,
    onBackground = VoziWhite,
    surface = Color.Black,
    onSurface = VoziWhite
)


@Composable
fun Vozi001Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // CAMBIO CLAVE AQUÍ:
    dynamicColor: Boolean = false, // <-- CAMBIA ESTO DE 'true' A 'false'
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Esta línea ya no se ejecutará si dynamicColor es 'false'
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme // <-- Usará nuestro tema claro
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}