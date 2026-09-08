package com.example.vehiclecare.domain.model

import java.time.LocalDate

data class Vehicle(
    val id: Long,
    val brand: String,
    val model: String,
    val plate: String,
    val year: Int,
    val mileageKm: Int,
    val isDefault: Boolean,
    val accent: VehicleAccent,
)

enum class VehicleAccent { Ocean, Amber, Violet }

data class MaintenanceTask(
    val id: Long,
    val vehicleId: Long,
    val title: String,
    val category: MaintenanceCategory,
    val dueDate: LocalDate?,
    val dueMileageKm: Int?,
    val lastCompletedAt: LocalDate?,
    val isCompleted: Boolean = false,
)

enum class MaintenanceCategory { OIL, TYRE, BRAKE, INSPECTION, BATTERY }

data class VehicleDocument(
    val id: Long,
    val vehicleId: Long,
    val title: String,
    val type: DocumentType,
    val expiresAt: LocalDate,
)

enum class DocumentType { INSURANCE, REGISTRATION, INSPECTION, CASCO }

data class ServicePoint(
    val id: Long,
    val name: String,
    val distanceKm: Double,
    val rating: Double,
    val reviewCount: Int,
    val specialties: List<String>,
    val isOpenNow: Boolean,
)
