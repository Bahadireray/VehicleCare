package com.example.vehiclecare.presentation.planner

import com.example.vehiclecare.core.common.UiLoadState
import com.example.vehiclecare.domain.model.MaintenanceTask

data class PlannerUiState(val items: UiLoadState<List<MaintenanceTask>> = UiLoadState.Loading)
sealed interface PlannerUiEvent { data class TaskCompleted(val id: Long) : PlannerUiEvent }
