package com.example.vehiclecare.presentation.profile

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vehiclecare.presentation.navigation.AppDestination

@Composable
fun ProfileRoute(onNavigate: (AppDestination) -> Unit, viewModel: ProfileViewModel = viewModel()) =
    ProfileScreen(
        viewModel.uiState.collectAsStateWithLifecycle().value,
        viewModel::onEvent,
        onNavigate
    )
