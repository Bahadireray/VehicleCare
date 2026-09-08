package com.example.vehiclecare.presentation.detail

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun VehicleDetailRoute(vehicleId: Long, onBack: () -> Unit, viewModel: VehicleDetailViewModel = hiltViewModel()) {
    val state = viewModel.stateFor(vehicleId).collectAsStateWithLifecycle().value
    VehicleDetailScreen(state = state, onEvent = viewModel::onEvent, onBack = onBack)
}
