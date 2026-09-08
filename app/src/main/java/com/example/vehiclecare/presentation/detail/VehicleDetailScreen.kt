package com.example.vehiclecare.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.EditRoad
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vehiclecare.core.common.UiLoadState
import com.example.vehiclecare.R
import com.example.vehiclecare.presentation.components.MaintenanceRow
import com.example.vehiclecare.presentation.components.formatKm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleDetailScreen(state: VehicleDetailUiState, onEvent: (VehicleDetailUiEvent) -> Unit, onBack: () -> Unit) {
    if (state.showMileageDialog) MileageDialog(onDismiss = { onEvent(VehicleDetailUiEvent.MileageDialogDismissed) }, onSave = { onEvent(VehicleDetailUiEvent.MileageSaved(it)) })
    Column(Modifier.fillMaxSize()) {
        TopAppBar(title = { Text("Vehicle overview") }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Outlined.ArrowBack, "Back") } })
        when (val vehicle = state.vehicle) {
            UiLoadState.Loading -> Text("Loading vehicle…", Modifier.padding(20.dp))
            is UiLoadState.Error -> Text("Vehicle not found", Modifier.padding(20.dp))
            is UiLoadState.Success -> LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                item {
                    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                        Column(Modifier.padding(20.dp)) {
                            Text("${vehicle.data.brand} ${vehicle.data.model}", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                            Text("${vehicle.data.plate} • ${vehicle.data.year}")
                            Spacer(Modifier.height(16.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Column(Modifier.weight(1f)) { Text("Current odometer"); Text("${vehicle.data.mileageKm.formatKm()} km", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold) }
                                IconButton(onClick = { onEvent(VehicleDetailUiEvent.MileageClicked) }) { Icon(Icons.Outlined.EditRoad, "Update mileage") }
                            }
                        }
                    }
                }
                item { Button(onClick = { onEvent(VehicleDetailUiEvent.DefaultClicked) }, modifier = Modifier.fillMaxWidth()) { Text(if (vehicle.data.isDefault) "Default vehicle" else "Make default vehicle") } }
                item { Text("Maintenance timeline", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) }
                items(state.maintenance, key = { it.id }) { task -> MaintenanceRow(task) { if (!task.isCompleted) onEvent(VehicleDetailUiEvent.MaintenanceCompleted(task.id)) } }
            }
        }
    }
}

@Composable private fun MileageDialog(onDismiss: () -> Unit, onSave: (String) -> Unit) {
    var value by remember { mutableStateOf("") }
    AlertDialog(onDismissRequest = onDismiss, title = { Text(stringResource(R.string.update_mileage)) }, text = { OutlinedTextField(value = value, onValueChange = { value = it }, label = { Text("Odometer in km") }, singleLine = true) }, confirmButton = { Button(onClick = { onSave(value) }) { Text(stringResource(R.string.save)) } }, dismissButton = { Button(onClick = onDismiss) { Text(stringResource(R.string.cancel)) } })
}
