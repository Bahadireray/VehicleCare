package com.example.vehiclecare.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vehiclecare.domain.model.MaintenanceTask
import com.example.vehiclecare.domain.model.Vehicle
import com.example.vehiclecare.domain.model.VehicleAccent
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun VehicleCard(vehicle: Vehicle, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val tint = when (vehicle.accent) {
        VehicleAccent.Ocean -> Color(0xFF006B5F); VehicleAccent.Amber -> Color(0xFF9C5E00); VehicleAccent.Violet -> Color(
            0xFF754EA3
        )
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(modifier = Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Outlined.DirectionsCar, null, tint = tint, modifier = Modifier.size(42.dp))
            Column(modifier = Modifier
                .weight(1f)
                .padding(start = 14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "${vehicle.brand} ${vehicle.model}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    if (vehicle.isDefault) StatusPill(
                        "DEFAULT",
                        tint,
                        Modifier.padding(start = 8.dp)
                    )
                }
                Text(
                    "${vehicle.plate} • ${vehicle.year}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "${vehicle.mileageKm.formatKm()} km",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Composable
fun StatusPill(label: String, color: Color, modifier: Modifier = Modifier) {
    Text(
        label,
        modifier = modifier
            .clip(RoundedCornerShape(99.dp))
            .background(color.copy(alpha = .14f))
            .padding(horizontal = 8.dp, vertical = 3.dp),
        color = color,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun MaintenanceRow(task: MaintenanceTask, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = .55f)
        )
    ) {
        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Outlined.Build, null, tint = MaterialTheme.colorScheme.primary)
            Column(modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)) {
                Text(task.title, fontWeight = FontWeight.Medium)
                Text(
                    task.dueLabel(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            StatusPill(
                if (task.isCompleted) "DONE" else "DUE",
                if (task.isCompleted) Color(0xFF137333) else Color(0xFFB25A00)
            )
        }
    }
}

fun Int.formatKm(): String = String.format(Locale.US, "%,d", this)
fun LocalDate.formatShort(): String =
    format(DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault()))

fun MaintenanceTask.dueLabel(): String = buildString {
    dueDate?.let { append("Due ${it.formatShort()}") }
    dueMileageKm?.let { if (isNotEmpty()) append(" • "); append("or ${it.formatKm()} km") }
}
