package com.example.vehiclecare.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.Upsert
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "vehicles")
data class VehicleEntity(
    @PrimaryKey val id: Long, val brand: String, val model: String, val plate: String,
    val year: Int, val mileageKm: Int, val isDefault: Boolean, val accent: String,
)

@Entity(tableName = "maintenance_tasks")
data class MaintenanceEntity(
    @PrimaryKey val id: Long, val vehicleId: Long, val title: String, val category: String,
    val dueDate: LocalDate?, val dueMileageKm: Int?, val lastCompletedAt: LocalDate?, val isCompleted: Boolean,
)

@Entity(tableName = "documents")
data class DocumentEntity(
    @PrimaryKey val id: Long, val vehicleId: Long, val title: String, val type: String, val expiresAt: LocalDate,
)

@Entity(tableName = "service_points")
data class ServiceEntity(
    @PrimaryKey val id: Long, val name: String, val distanceKm: Double, val rating: Double,
    val reviewCount: Int, val specialtiesCsv: String, val isOpenNow: Boolean,
)

@Dao
interface VehicleDao {
    @Query("SELECT * FROM vehicles ORDER BY isDefault DESC, brand, model") fun observeVehicles(): Flow<List<VehicleEntity>>
    @Query("SELECT * FROM vehicles WHERE id = :id") fun observeVehicle(id: Long): Flow<VehicleEntity?>
    @Query("SELECT * FROM maintenance_tasks WHERE vehicleId = :vehicleId ORDER BY isCompleted, dueDate") fun observeMaintenance(vehicleId: Long): Flow<List<MaintenanceEntity>>
    @Query("SELECT * FROM documents ORDER BY expiresAt") fun observeDocuments(): Flow<List<DocumentEntity>>
    @Query("SELECT * FROM service_points ORDER BY distanceKm") fun observeServices(): Flow<List<ServiceEntity>>
    @Query("SELECT COUNT(*) FROM vehicles") suspend fun vehicleCount(): Int
    @Query("UPDATE vehicles SET mileageKm = :mileageKm WHERE id = :vehicleId") suspend fun updateMileage(vehicleId: Long, mileageKm: Int)
    @Query("UPDATE maintenance_tasks SET isCompleted = 1, lastCompletedAt = :completedAt WHERE id = :taskId") suspend fun completeTask(taskId: Long, completedAt: LocalDate)
    @Query("UPDATE vehicles SET isDefault = CASE WHEN id = :vehicleId THEN 1 ELSE 0 END") suspend fun setDefault(vehicleId: Long)
    @Upsert suspend fun upsertVehicles(items: List<VehicleEntity>)
    @Upsert suspend fun upsertMaintenance(items: List<MaintenanceEntity>)
    @Upsert suspend fun upsertDocuments(items: List<DocumentEntity>)
    @Upsert suspend fun upsertServices(items: List<ServiceEntity>)
}

class DateConverters {
    @TypeConverter fun toDate(value: String?): LocalDate? = value?.let(LocalDate::parse)
    @TypeConverter fun fromDate(value: LocalDate?): String? = value?.toString()
}

@Database(entities = [VehicleEntity::class, MaintenanceEntity::class, DocumentEntity::class, ServiceEntity::class], version = 1, exportSchema = true)
@TypeConverters(DateConverters::class)
abstract class VehicleDatabase : RoomDatabase() { abstract fun vehicleDao(): VehicleDao }
