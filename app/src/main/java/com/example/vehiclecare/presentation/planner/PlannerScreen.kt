package com.example.vehiclecare.presentation.planner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vehiclecare.core.common.UiLoadState
import com.example.vehiclecare.R
import com.example.vehiclecare.presentation.components.MaintenanceRow
import com.example.vehiclecare.presentation.navigation.AppDestination

@Composable
fun PlannerScreen(
    state: PlannerUiState,
    onEvent: (PlannerUiEvent) -> Unit,
    onNavigate: (AppDestination) -> Unit
) {
    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                stringResource(R.string.maintenance_plan),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        item {
            Text(
                "For Toyota Corolla Hybrid • time and mileage triggers",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        when (val data = state.items) {
            UiLoadState.Loading -> item { Text("Building your plan…") }
            is UiLoadState.Error -> item { Text("Could not load maintenance") }
            is UiLoadState.Success -> items(data.data) { task ->
                MaintenanceRow(task) {
                    onEvent(
                        PlannerUiEvent.TaskCompleted(task.id)
                    )
                }
            }
        }
    }
}
