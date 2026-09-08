package com.example.vehiclecare.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF006B5F), onPrimary = Color.White, primaryContainer = Color(0xFF8FF8E5),
    secondary = Color(0xFF475D92), tertiary = Color(0xFF7A519D), surface = Color(0xFFFAFAF7),
    surfaceVariant = Color(0xFFE9EFE9), outline = Color(0xFF717875), error = Color(0xFFBA1A1A),
)
private val DarkColors = darkColorScheme(
    primary = Color(0xFF72DBCB), onPrimary = Color(0xFF003731), primaryContainer = Color(0xFF005047),
    secondary = Color(0xFFB9C5FF), tertiary = Color(0xFFE8B8FF), surface = Color(0xFF111412),
)

@Composable fun VehicleCareTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = LightColors, typography = VehicleTypography, content = content)
}
