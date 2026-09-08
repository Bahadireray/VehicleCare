package com.example.vehiclecare.presentation.services

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vehiclecare.presentation.navigation.AppDestination

@Composable fun ServicesRoute(onNavigate: (AppDestination) -> Unit, viewModel: ServicesViewModel = hiltViewModel()) = ServicesScreen(viewModel.uiState.collectAsStateWithLifecycle().value, viewModel::onEvent, onNavigate)
