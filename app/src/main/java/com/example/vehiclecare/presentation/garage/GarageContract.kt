package com.example.vehiclecare.presentation.garage

import com.example.vehiclecare.core.common.UiLoadState
import com.example.vehiclecare.domain.model.Vehicle

data class GarageUiState(
    val vehicles: UiLoadState<List<Vehicle>> = UiLoadState.Loading,
    val filter: String = "",
)

sealed interface GarageUiEvent {
    data class FilterChanged(val value: String) : GarageUiEvent
    data class VehicleClicked(val id: Long) : GarageUiEvent
    data class DefaultVehicleClicked(val id: Long) : GarageUiEvent
}
