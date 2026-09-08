package com.example.vehiclecare.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vehiclecare.presentation.navigation.AppDestination
import com.example.vehiclecare.R

@Composable
fun ProfileScreen(
    state: ProfileUiState,
    onEvent: (ProfileUiEvent) -> Unit,
    onNavigate: (AppDestination) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            stringResource(R.string.profile_preferences),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Card {
            Column(Modifier.padding(18.dp)) {
                Text(
                    state.ownerName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                ); Text("Vehicle care member")
            }
        }
        Card {
            androidx.compose.foundation.layout.Row(
                Modifier.padding(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text(
                        "Maintenance reminders",
                        fontWeight = FontWeight.SemiBold
                    ); Text(
                    "Notifications are represented in the case; schedule WorkManager in production.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                }; Switch(
                checked = state.remindersEnabled,
                onCheckedChange = { onEvent(ProfileUiEvent.RemindersToggled) })
            }
        }
        Card {
            Column(Modifier.padding(18.dp)) {
                Text(
                    "Language",
                    fontWeight = FontWeight.SemiBold
                ); Text(
                "English and Turkish Android resources are included. The system or per-app language chooses the active resource set.",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            }
        }
    }
}
