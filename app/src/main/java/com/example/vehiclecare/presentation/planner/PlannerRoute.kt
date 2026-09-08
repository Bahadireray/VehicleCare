package com.example.vehiclecare.presentation.planner

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vehiclecare.presentation.navigation.AppDestination

@Composable
fun PlannerRoute(
    onNavigate: (AppDestination) -> Unit,
    viewModel: PlannerViewModel = hiltViewModel()
) {
    PlannerScreen(
        viewModel.uiState.collectAsStateWithLifecycle().value,
        viewModel::onEvent,
        onNavigate
    )
}
