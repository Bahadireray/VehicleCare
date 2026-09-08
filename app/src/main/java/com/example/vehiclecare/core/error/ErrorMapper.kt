package com.example.vehiclecare.core.error

import com.example.vehiclecare.R
import com.example.vehiclecare.core.common.UiText
import java.io.IOException
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

sealed interface AppError {
    data object Network : AppError
    data object Unknown : AppError
    data class Validation(val message: UiText) : AppError
}

class ErrorMapper @Inject constructor() {
    fun map(throwable: Throwable): AppError {
        if (throwable is CancellationException) throw throwable
        return when (throwable) {
            is IOException -> AppError.Network
            else -> AppError.Unknown
        }
    }
    fun asUiText(error: AppError): UiText = when (error) {
        AppError.Network -> UiText.Resource(R.string.error_network)
        AppError.Unknown -> UiText.Resource(R.string.error_unknown)
        is AppError.Validation -> error.message
    }
}
