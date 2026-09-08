package com.example.vehiclecare.presentation.documents

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vehiclecare.presentation.navigation.AppDestination

@Composable fun DocumentsRoute(onNavigate: (AppDestination) -> Unit, viewModel: DocumentsViewModel = hiltViewModel()) = DocumentsScreen(viewModel.uiState.collectAsStateWithLifecycle().value, viewModel::onEvent, onNavigate)
