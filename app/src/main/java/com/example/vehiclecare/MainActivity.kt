package com.example.vehiclecare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.vehiclecare.presentation.navigation.VehicleCareApp
import com.example.vehiclecare.core.ui.theme.VehicleCareTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { VehicleCareTheme { VehicleCareApp() } }
    }
}
