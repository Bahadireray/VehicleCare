package com.example.vehiclecare.presentation.detail

import com.example.vehiclecare.core.common.UiLoadState
import com.example.vehiclecare.domain.model.MaintenanceTask
import com.example.vehiclecare.domain.model.Vehicle

data class VehicleDetailUiState(
    val vehicle: UiLoadState<Vehicle> = UiLoadState.Loading,
    val maintenance: List<MaintenanceTask> = emptyList(),
    val showMileageDialog: Boolean = false,
)

sealed interface VehicleDetailUiEvent {
    data object MileageClicked : VehicleDetailUiEvent
    data object MileageDialogDismissed : VehicleDetailUiEvent
    data class MileageSaved(val value: String) : VehicleDetailUiEvent
    data class MaintenanceCompleted(val taskId: Long) : VehicleDetailUiEvent
    data object DefaultClicked : VehicleDetailUiEvent
}
