package com.example.vehiclecare.di

import android.content.Context
import androidx.room.Room
import com.example.vehiclecare.data.local.VehicleDao
import com.example.vehiclecare.data.local.VehicleDatabase
import com.example.vehiclecare.data.repository.OfflineFirstVehicleRepository
import com.example.vehiclecare.domain.repository.VehicleRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds abstract fun bindVehicleRepository(implementation: OfflineFirstVehicleRepository): VehicleRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides @Singleton fun provideDatabase(@ApplicationContext context: Context): VehicleDatabase =
        Room.databaseBuilder(context, VehicleDatabase::class.java, "vehicle-care.db").fallbackToDestructiveMigration().build()
    @Provides fun provideVehicleDao(database: VehicleDatabase): VehicleDao = database.vehicleDao()
}
