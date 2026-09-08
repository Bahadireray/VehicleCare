package com.example.vehiclecare.presentation.garage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehiclecare.core.common.UiLoadState
import com.example.vehiclecare.domain.usecase.ObserveVehiclesUseCase
import com.example.vehiclecare.domain.usecase.SetDefaultVehicleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class GarageViewModel @Inject constructor(
    observeVehicles: ObserveVehiclesUseCase,
    private val setDefaultVehicle: SetDefaultVehicleUseCase,
) : ViewModel() {
    private val filter = MutableStateFlow("")
    val uiState = combine(observeVehicles(), filter) { vehicles, query ->
        GarageUiState(UiLoadState.Success(vehicles.filter {
            "${it.brand} ${it.model} ${it.plate}".contains(
                query,
                ignoreCase = true
            )
        }), query)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), GarageUiState())

    fun onEvent(event: GarageUiEvent) = when (event) {
        is GarageUiEvent.FilterChanged -> filter.value = event.value
        is GarageUiEvent.DefaultVehicleClicked -> viewModelScope.launch { setDefaultVehicle(event.id) }
        is GarageUiEvent.VehicleClicked -> Unit
    }
}
