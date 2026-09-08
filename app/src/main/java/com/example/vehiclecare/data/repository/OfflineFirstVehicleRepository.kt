package com.example.vehiclecare.data.repository

import com.example.vehiclecare.data.local.DocumentEntity
import com.example.vehiclecare.data.local.MaintenanceEntity
import com.example.vehiclecare.data.local.ServiceEntity
import com.example.vehiclecare.data.local.VehicleDao
import com.example.vehiclecare.data.local.VehicleEntity
import com.example.vehiclecare.data.mock.MockData
import com.example.vehiclecare.domain.model.DocumentType
import com.example.vehiclecare.domain.model.MaintenanceCategory
import com.example.vehiclecare.domain.model.MaintenanceTask
import com.example.vehiclecare.domain.model.ServicePoint
import com.example.vehiclecare.domain.model.Vehicle
import com.example.vehiclecare.domain.model.VehicleAccent
import com.example.vehiclecare.domain.model.VehicleDocument
import com.example.vehiclecare.domain.repository.VehicleRepository
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineFirstVehicleRepository @Inject constructor(private val dao: VehicleDao) : VehicleRepository {
    override fun observeVehicles(): Flow<List<Vehicle>> = dao.observeVehicles().map { it.map(VehicleEntity::toDomain) }
    override fun observeVehicle(id: Long): Flow<Vehicle?> = dao.observeVehicle(id).map { it?.toDomain() }
    override fun observeMaintenance(vehicleId: Long): Flow<List<MaintenanceTask>> = dao.observeMaintenance(vehicleId).map { it.map(MaintenanceEntity::toDomain) }
    override fun observeDocuments(): Flow<List<VehicleDocument>> = dao.observeDocuments().map { it.map(DocumentEntity::toDomain) }
    override fun observeServices(): Flow<List<ServicePoint>> = dao.observeServices().map { it.map(ServiceEntity::toDomain) }
    override suspend fun updateMileage(vehicleId: Long, mileageKm: Int) = dao.updateMileage(vehicleId, mileageKm)
    override suspend fun completeMaintenance(taskId: Long) = dao.completeTask(taskId, LocalDate.now())
    override suspend fun setDefaultVehicle(vehicleId: Long) = dao.setDefault(vehicleId)
    suspend fun seedIfNeeded() { if (dao.vehicleCount() == 0) { dao.upsertVehicles(MockData.vehicles); dao.upsertMaintenance(MockData.maintenance); dao.upsertDocuments(MockData.documents); dao.upsertServices(MockData.services) } }
}

private fun VehicleEntity.toDomain() = Vehicle(id, brand, model, plate, year, mileageKm, isDefault, VehicleAccent.valueOf(accent))
private fun MaintenanceEntity.toDomain() = MaintenanceTask(id, vehicleId, title, MaintenanceCategory.valueOf(category), dueDate, dueMileageKm, lastCompletedAt, isCompleted)
private fun DocumentEntity.toDomain() = VehicleDocument(id, vehicleId, title, DocumentType.valueOf(type), expiresAt)
private fun ServiceEntity.toDomain() = ServicePoint(id, name, distanceKm, rating, reviewCount, specialtiesCsv.split(','), isOpenNow)
