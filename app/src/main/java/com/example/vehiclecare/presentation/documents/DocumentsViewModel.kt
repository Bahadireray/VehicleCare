package com.example.vehiclecare.presentation.documents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehiclecare.core.common.UiLoadState
import com.example.vehiclecare.domain.usecase.ObserveDocumentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel class DocumentsViewModel @Inject constructor(observe: ObserveDocumentsUseCase) : ViewModel() {
    val uiState = observe().map { DocumentsUiState(UiLoadState.Success(it)) }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), DocumentsUiState())
    fun onEvent(event: DocumentsUiEvent) = Unit
}
