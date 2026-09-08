package com.example.vehiclecare.core.common

sealed interface UiLoadState<out T> {
    data object Loading : UiLoadState<Nothing>
    data class Success<T>(val data: T) : UiLoadState<T>
    data class Error(val message: UiText) : UiLoadState<Nothing>
}
