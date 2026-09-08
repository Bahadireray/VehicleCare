package com.example.vehiclecare.presentation.documents

import com.example.vehiclecare.core.common.UiLoadState
import com.example.vehiclecare.domain.model.VehicleDocument

data class DocumentsUiState(val documents: UiLoadState<List<VehicleDocument>> = UiLoadState.Loading)
sealed interface DocumentsUiEvent {
    data class DocumentClicked(val id: Long) : DocumentsUiEvent
}
