package com.example.vehiclecare.presentation.garage

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vehiclecare.presentation.navigation.AppDestination

@Composable
fun GarageRoute(onOpenVehicle: (Long) -> Unit, onNavigate: (AppDestination) -> Unit, viewModel: GarageViewModel = hiltViewModel()) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    GarageScreen(state = state, onEvent = { event ->
        viewModel.onEvent(event)
        if (event is GarageUiEvent.VehicleClicked) onOpenVehicle(event.id)
    }, onNavigate = onNavigate)
}
