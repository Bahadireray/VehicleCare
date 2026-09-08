package com.example.vehiclecare.presentation.services

import com.example.vehiclecare.core.common.UiLoadState
import com.example.vehiclecare.domain.model.ServicePoint

data class ServicesUiState(val services: UiLoadState<List<ServicePoint>> = UiLoadState.Loading, val showOpenOnly: Boolean = false)
sealed interface ServicesUiEvent { data object OpenOnlyToggled : ServicesUiEvent; data class ServiceClicked(val id: Long) : ServicesUiEvent }
