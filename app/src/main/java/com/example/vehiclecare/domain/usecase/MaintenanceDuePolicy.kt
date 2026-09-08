package com.example.vehiclecare.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.vehiclecare.domain.model.MaintenanceTask
import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class MaintenanceDueStatus(val isDueSoon: Boolean, val reason: DueReason?)
enum class DueReason { DATE, MILEAGE }

class MaintenanceDuePolicy {
    @RequiresApi(Build.VERSION_CODES.O)
    fun evaluate(
        task: MaintenanceTask,
        currentMileageKm: Int,
        today: LocalDate = LocalDate.now()
    ): MaintenanceDueStatus {
        if (task.isCompleted) return MaintenanceDueStatus(false, null)
        val dateDue = task.dueDate?.let { ChronoUnit.DAYS.between(today, it) <= 30 } ?: false
        val mileageDue = task.dueMileageKm?.let { it - currentMileageKm <= 1_000 } ?: false
        return when {
            dateDue -> MaintenanceDueStatus(
                true,
                DueReason.DATE
            ); mileageDue -> MaintenanceDueStatus(
                true,
                DueReason.MILEAGE
            ); else -> MaintenanceDueStatus(false, null)
        }
    }
}
