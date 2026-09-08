package com.example.vehiclecare.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehiclecare.R
import com.example.vehiclecare.core.common.UiLoadState
import com.example.vehiclecare.core.common.UiText
import com.example.vehiclecare.domain.usecase.CompleteMaintenanceUseCase
import com.example.vehiclecare.domain.usecase.ObserveMaintenanceUseCase
import com.example.vehiclecare.domain.usecase.ObserveVehicleUseCase
import com.example.vehiclecare.domain.usecase.SetDefaultVehicleUseCase
import com.example.vehiclecare.domain.usecase.UpdateMileageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class VehicleDetailViewModel @Inject constructor(
    private val observeVehicleUseCase: ObserveVehicleUseCase,
    private val observeMaintenanceUseCase: ObserveMaintenanceUseCase,
    private val updateMileageUseCase: UpdateMileageUseCase,
    private val completeMaintenanceUseCase: CompleteMaintenanceUseCase,
    private val setDefaultVehicleUseCase: SetDefaultVehicleUseCase,
) : ViewModel() {

    private val vehicleId = MutableStateFlow(1L)

    private val dialogVisible = MutableStateFlow(false)

    private val mileageError = MutableStateFlow<UiText?>(null)

    private var stateSource: StateFlow<VehicleDetailUiState>? = null

    fun stateFor(id: Long): StateFlow<VehicleDetailUiState> {
        if (stateSource == null || vehicleId.value != id) {
            vehicleId.value = id

            stateSource = combine(
                observeVehicleUseCase(id),
                observeMaintenanceUseCase(id),
                dialogVisible,
                mileageError,
            ) { vehicle, tasks, dialog, error ->

                VehicleDetailUiState(
                    vehicle = vehicle?.let {
                        UiLoadState.Success(it)
                    } ?: UiLoadState.Error(
                        UiText.Resource(R.string.vehicle_not_found)
                    ),
                    maintenance = tasks,
                    showMileageDialog = dialog
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = VehicleDetailUiState(),
            )
        }

        return requireNotNull(stateSource)
    }

    fun onEvent(event: VehicleDetailUiEvent) {
        when (event) {

            VehicleDetailUiEvent.MileageClicked -> {
                mileageError.value = null
                dialogVisible.value = true
            }

            VehicleDetailUiEvent.MileageDialogDismissed -> {
                mileageError.value = null
                dialogVisible.value = false
            }

            is VehicleDetailUiEvent.MileageSaved -> {
                saveMileage(event.value)
            }

            is VehicleDetailUiEvent.MaintenanceCompleted -> {
                completeMaintenance(event.taskId)
            }

            VehicleDetailUiEvent.DefaultClicked -> {
                setDefaultVehicle()
            }
        }
    }

    private fun saveMileage(value: String) {
        val mileage = value
            .trim()
            .toIntOrNull()

        if (mileage == null || mileage < 0) {
            mileageError.value = UiText.Dynamic(
                "Please enter a valid mileage."
            )
            return
        }

        mileageError.value = null

        viewModelScope.launch {
            updateMileageUseCase(
                vehicleId.value,
                mileage,
            )

            dialogVisible.value = false
        }
    }

    private fun completeMaintenance(taskId: Long) {
        viewModelScope.launch {
            completeMaintenanceUseCase(taskId)
        }
    }

    private fun setDefaultVehicle() {
        viewModelScope.launch {
            setDefaultVehicleUseCase(vehicleId.value)
        }
    }
}