package com.example.vehiclecare.presentation.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()
    fun onEvent(event: ProfileUiEvent) {
        if (event is ProfileUiEvent.RemindersToggled) _uiState.value =
            _uiState.value.copy(remindersEnabled = !_uiState.value.remindersEnabled)
    }
}
