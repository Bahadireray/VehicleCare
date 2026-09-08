package com.example.vehiclecare.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

typealias AppDestination = NavKey

@Serializable
data object GarageDestination : NavKey
@Serializable
data class VehicleDetailDestination(val vehicleId: Long) : NavKey
@Serializable
data object PlannerDestination : NavKey
@Serializable
data object DocumentsDestination : NavKey
@Serializable
data object ServicesDestination : NavKey
@Serializable
data object ProfileDestination : NavKey
