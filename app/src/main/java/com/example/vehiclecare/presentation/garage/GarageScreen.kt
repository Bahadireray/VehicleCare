package com.example.vehiclecare.presentation.garage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Garage
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vehiclecare.core.common.UiLoadState
import com.example.vehiclecare.R
import com.example.vehiclecare.presentation.components.VehicleCard
import com.example.vehiclecare.presentation.navigation.AppDestination
import com.example.vehiclecare.presentation.navigation.DocumentsDestination
import com.example.vehiclecare.presentation.navigation.PlannerDestination
import com.example.vehiclecare.presentation.navigation.ProfileDestination
import com.example.vehiclecare.presentation.navigation.ServicesDestination

@Composable
fun GarageScreen(
    state: GarageUiState,
    onEvent: (GarageUiEvent) -> Unit,
    onNavigate: (AppDestination) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                stringResource(R.string.garage_title),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                stringResource(R.string.garage_subtitle),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF006B5F))) {
                Column(Modifier.padding(20.dp)) {
                    Text(
                        "2 actions need attention",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(6.dp)); Text(
                    "Oil service and insurance renewal are coming up.",
                    color = Color(0xFFD2FFF5)
                )
                }
            }
        }
        item {
            OutlinedTextField(
                value = state.filter,
                onValueChange = { onEvent(GarageUiEvent.FilterChanged(it)) },
                label = { Text("Search vehicles") },
                leadingIcon = { Icon(Icons.Outlined.Search, null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
        item { QuickActions(onNavigate) }
        item {
            Text(
                "My vehicles",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        when (val content = state.vehicles) {
            UiLoadState.Loading -> item { Text("Loading your garage…") }
            is UiLoadState.Error -> item { Text("Could not load vehicles") }
            is UiLoadState.Success -> items(content.data, key = { it.id }) { vehicle ->
                VehicleCard(
                    vehicle = vehicle,
                    onClick = { onEvent(GarageUiEvent.VehicleClicked(vehicle.id)) })
            }
        }
    }
}

@Composable
private fun QuickActions(onNavigate: (AppDestination) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            "Quick access",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        androidx.compose.foundation.layout.Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            QuickChip("Plan", Icons.Outlined.Today) { onNavigate(PlannerDestination) }
            QuickChip("Documents", Icons.Outlined.Description) { onNavigate(DocumentsDestination) }
            QuickChip("Services", Icons.Outlined.LocationOn) { onNavigate(ServicesDestination) }
            QuickChip("Profile", Icons.Outlined.AccountCircle) { onNavigate(ProfileDestination) }
        }
    }
}

@Composable
private fun QuickChip(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    AssistChip(
        onClick = onClick,
        label = { Text(label) },
        leadingIcon = { Icon(icon, null) },
        colors = AssistChipDefaults.assistChipColors()
    )
}
