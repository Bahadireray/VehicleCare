package com.example.vehiclecare.presentation.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehiclecare.core.common.UiLoadState
import com.example.vehiclecare.domain.usecase.ObserveServicesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class ServicesViewModel @Inject constructor(observe: ObserveServicesUseCase) : ViewModel() {
    private val openOnly = MutableStateFlow(false)
    val uiState = combine(
        observe(),
        openOnly
    ) { services, onlyOpen ->
        ServicesUiState(UiLoadState.Success(if (onlyOpen) services.filter { it.isOpenNow } else services),
            onlyOpen)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ServicesUiState())

    fun onEvent(event: ServicesUiEvent) {
        if (event is ServicesUiEvent.OpenOnlyToggled) openOnly.value = !openOnly.value
    }
}
