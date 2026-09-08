package com.example.vehiclecare.data.mock

import com.example.vehiclecare.data.local.DocumentEntity
import com.example.vehiclecare.data.local.MaintenanceEntity
import com.example.vehiclecare.data.local.ServiceEntity
import com.example.vehiclecare.data.local.VehicleEntity
import java.time.LocalDate

/** Replace this seed source with Retrofit synchronisation without changing the UI contracts. */
object MockData {
    val vehicles = listOf(
        VehicleEntity(1, "Toyota", "Corolla Hybrid", "34 VCC 34", 2022, 48_320, true, "Ocean"),
        VehicleEntity(2, "Volvo", "XC40", "06 BKC 06", 2021, 31_780, false, "Violet"),
        VehicleEntity(3, "Fiat", "Egea", "35 IZM 35", 2020, 72_450, false, "Amber"),
    )
    val maintenance = listOf(
        MaintenanceEntity(101, 1, "Engine oil & filter", "OIL", LocalDate.of(2026, 10, 6), 50_000, LocalDate.of(2026, 4, 6), false),
        MaintenanceEntity(102, 1, "Tyre rotation", "TYRE", LocalDate.of(2026, 11, 20), 54_000, LocalDate.of(2026, 2, 20), false),
        MaintenanceEntity(103, 1, "Brake fluid check", "BRAKE", LocalDate.of(2027, 3, 12), null, LocalDate.of(2025, 3, 12), false),
        MaintenanceEntity(104, 2, "Annual inspection", "INSPECTION", LocalDate.of(2026, 9, 27), null, LocalDate.of(2025, 9, 27), false),
        MaintenanceEntity(105, 2, "Battery health check", "BATTERY", LocalDate.of(2027, 1, 15), null, LocalDate.of(2026, 1, 15), false),
        MaintenanceEntity(106, 3, "Engine oil & filter", "OIL", LocalDate.of(2026, 12, 1), 78_000, LocalDate.of(2026, 4, 1), false),
    )
    val documents = listOf(
        DocumentEntity(201, 1, "Compulsory traffic insurance", "INSURANCE", LocalDate.of(2026, 10, 2)),
        DocumentEntity(202, 1, "Vehicle inspection certificate", "INSPECTION", LocalDate.of(2027, 5, 14)),
        DocumentEntity(203, 2, "Comprehensive insurance", "CASCO", LocalDate.of(2026, 9, 24)),
        DocumentEntity(204, 3, "Vehicle registration", "REGISTRATION", LocalDate.of(2029, 1, 1)),
    )
    val services = listOf(
        ServiceEntity(301, "Marmara Auto Lab", 1.2, 4.8, 126, "Hybrid,Diagnostics,Oil", true),
        ServiceEntity(302, "North Garage", 2.7, 4.6, 82, "Tyres,Brakes,Alignment", true),
        ServiceEntity(303, "Bosporus Service Point", 4.1, 4.9, 219, "Inspection,Electrical,Detailing", false),
    )
}
