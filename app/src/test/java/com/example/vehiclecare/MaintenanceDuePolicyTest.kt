package com.example.vehiclecare

import com.example.vehiclecare.domain.model.MaintenanceCategory
import com.example.vehiclecare.domain.model.MaintenanceTask
import com.example.vehiclecare.domain.usecase.DueReason
import com.example.vehiclecare.domain.usecase.MaintenanceDuePolicy
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test

class MaintenanceDuePolicyTest {
    private val policy = MaintenanceDuePolicy()
    private val today = LocalDate.of(2026, 9, 8)

    @Test fun `mileage trigger is due within 1000 km`() {
        val task = task(dueDate = LocalDate.of(2027, 2, 1), dueMileage = 50_000)
        assertEquals(DueReason.MILEAGE, policy.evaluate(task, currentMileageKm = 49_150, today).reason)
    }

    @Test fun `completed task is not due`() {
        assertEquals(false, policy.evaluate(task(dueDate = today.plusDays(1), dueMileage = 40_000).copy(isCompleted = true), 39_900, today).isDueSoon)
    }

    private fun task(dueDate: LocalDate, dueMileage: Int) = MaintenanceTask(1, 1, "Oil", MaintenanceCategory.OIL, dueDate, dueMileage, null)
}
