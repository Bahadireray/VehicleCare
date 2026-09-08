package com.example.vehiclecare.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehiclecare.core.common.UiText
import com.example.vehiclecare.core.error.ErrorMapper
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel(private val errorMapper: ErrorMapper) : ViewModel() {
    private val _messages = MutableSharedFlow<UiText>(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val messages: SharedFlow<UiText> = _messages

    protected fun launchSafely(block: suspend () -> Unit) = viewModelScope.launch {
        runCatching { block() }.onFailure { _messages.tryEmit(errorMapper.asUiText(errorMapper.map(it))) }
    }

    protected fun sendMessage(message: UiText) { _messages.tryEmit(message) }
}
