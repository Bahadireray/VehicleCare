package com.example.vehiclecare.presentation.profile

data class ProfileUiState(val ownerName: String = "Alex Morgan", val remindersEnabled: Boolean = true)
sealed interface ProfileUiEvent { data object RemindersToggled : ProfileUiEvent }
