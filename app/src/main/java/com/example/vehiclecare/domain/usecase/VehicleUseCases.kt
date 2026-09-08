package com.example.vehiclecare.domain.usecase

import com.example.vehiclecare.domain.repository.VehicleRepository
import javax.inject.Inject

class ObserveVehiclesUseCase @Inject constructor(private val repository: VehicleRepository) {
    operator fun invoke() = repository.observeVehicles()
}
class ObserveVehicleUseCase @Inject constructor(private val repository: VehicleRepository) {
    operator fun invoke(id: Long) = repository.observeVehicle(id)
}
class ObserveMaintenanceUseCase @Inject constructor(private val repository: VehicleRepository) {
    operator fun invoke(vehicleId: Long) = repository.observeMaintenance(vehicleId)
}
class ObserveDocumentsUseCase @Inject constructor(private val repository: VehicleRepository) {
    operator fun invoke() = repository.observeDocuments()
}
class ObserveServicesUseCase @Inject constructor(private val repository: VehicleRepository) {
    operator fun invoke() = repository.observeServices()
}
class UpdateMileageUseCase @Inject constructor(private val repository: VehicleRepository) {
    suspend operator fun invoke(vehicleId: Long, mileageKm: Int) = repository.updateMileage(vehicleId, mileageKm)
}
class CompleteMaintenanceUseCase @Inject constructor(private val repository: VehicleRepository) {
    suspend operator fun invoke(taskId: Long) = repository.completeMaintenance(taskId)
}
class SetDefaultVehicleUseCase @Inject constructor(private val repository: VehicleRepository) {
    suspend operator fun invoke(vehicleId: Long) = repository.setDefaultVehicle(vehicleId)
}
