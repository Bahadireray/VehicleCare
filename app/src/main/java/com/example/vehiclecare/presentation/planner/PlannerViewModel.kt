package com.example.vehiclecare.presentation.planner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehiclecare.core.common.UiLoadState
import com.example.vehiclecare.domain.usecase.CompleteMaintenanceUseCase
import com.example.vehiclecare.domain.usecase.ObserveMaintenanceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class PlannerViewModel @Inject constructor(observeMaintenance: ObserveMaintenanceUseCase, private val complete: CompleteMaintenanceUseCase) : ViewModel() {
    val uiState = observeMaintenance(1).map { PlannerUiState(UiLoadState.Success(it)) }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), PlannerUiState())
    fun onEvent(event: PlannerUiEvent) { if (event is PlannerUiEvent.TaskCompleted) viewModelScope.launch { complete(event.id) } }
}
