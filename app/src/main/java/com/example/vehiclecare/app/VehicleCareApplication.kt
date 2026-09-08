package com.example.vehiclecare.app

import android.app.Application
import com.example.vehiclecare.data.repository.OfflineFirstVehicleRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@HiltAndroidApp
class VehicleCareApplication : Application() {
    @Inject
    lateinit var repository: OfflineFirstVehicleRepository
    override fun onCreate() {
        super.onCreate()
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch { repository.seedIfNeeded() }
    }
}
