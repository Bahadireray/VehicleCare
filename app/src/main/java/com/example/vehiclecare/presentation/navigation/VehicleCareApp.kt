package com.example.vehiclecare.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.vehiclecare.presentation.detail.VehicleDetailRoute
import com.example.vehiclecare.presentation.documents.DocumentsRoute
import com.example.vehiclecare.presentation.garage.GarageRoute
import com.example.vehiclecare.presentation.planner.PlannerRoute
import com.example.vehiclecare.presentation.profile.ProfileRoute
import com.example.vehiclecare.presentation.services.ServicesRoute

/** Navigation 3 owns a typed, saveable List-backed back stack. Routes own VM wiring; screens stay stateless. */
@Composable
fun VehicleCareApp() {
    val backStack = rememberNavBackStack(GarageDestination)
    val entries = entryProvider {
        entry<GarageDestination> {
            GarageRoute(
                onOpenVehicle = { backStack.add(VehicleDetailDestination(it)) },
                onNavigate = { destination -> backStack.add(destination) },
            )
        }
        entry<VehicleDetailDestination> { destination ->
            VehicleDetailRoute(
                vehicleId = destination.vehicleId,
                onBack = { backStack.removeLastOrNull() })
        }
        entry<PlannerDestination> { PlannerRoute(onNavigate = { backStack.add(it) }) }
        entry<DocumentsDestination> { DocumentsRoute(onNavigate = { backStack.add(it) }) }
        entry<ServicesDestination> { ServicesRoute(onNavigate = { backStack.add(it) }) }
        entry<ProfileDestination> { ProfileRoute(onNavigate = { backStack.add(it) }) }
    }
    Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entries,
            modifier = Modifier.fillMaxSize(),
        )
    }
}
