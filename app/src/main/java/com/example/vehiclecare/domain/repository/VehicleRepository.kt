package com.example.vehiclecare.domain.repository

import com.example.vehiclecare.domain.model.MaintenanceTask
import com.example.vehiclecare.domain.model.ServicePoint
import com.example.vehiclecare.domain.model.Vehicle
import com.example.vehiclecare.domain.model.VehicleDocument
import kotlinx.coroutines.flow.Flow

interface VehicleRepository {
    fun observeVehicles(): Flow<List<Vehicle>>
    fun observeVehicle(id: Long): Flow<Vehicle?>
    fun observeMaintenance(vehicleId: Long): Flow<List<MaintenanceTask>>
    fun observeDocuments(): Flow<List<VehicleDocument>>
    fun observeServices(): Flow<List<ServicePoint>>
    suspend fun updateMileage(vehicleId: Long, mileageKm: Int)
    suspend fun completeMaintenance(taskId: Long)
    suspend fun setDefaultVehicle(vehicleId: Long)
}
