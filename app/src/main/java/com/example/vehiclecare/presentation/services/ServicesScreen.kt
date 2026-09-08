package com.example.vehiclecare.presentation.services

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vehiclecare.core.common.UiLoadState
import com.example.vehiclecare.R
import com.example.vehiclecare.presentation.components.StatusPill
import com.example.vehiclecare.presentation.navigation.AppDestination

@Composable
fun ServicesScreen(
    state: ServicesUiState,
    onEvent: (ServicesUiEvent) -> Unit,
    onNavigate: (AppDestination) -> Unit
) {
    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                stringResource(R.string.service_finder),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        item {
            Text(
                "Mock location results, ranked by distance and rating.",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        item {
            FilterChip(
                selected = state.showOpenOnly,
                onClick = { onEvent(ServicesUiEvent.OpenOnlyToggled) },
                label = { Text("Open now") })
        }
        when (val content = state.services) {
            UiLoadState.Loading -> item { Text("Finding nearby services…") }
            is UiLoadState.Error -> item { Text("Could not load services") }
            is UiLoadState.Success -> items(content.data) { service ->
                androidx.compose.material3.Card {
                    Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Outlined.LocationOn,
                            null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Column(Modifier
                            .weight(1f)
                            .padding(start = 12.dp)) {
                            Text(
                                service.name,
                                fontWeight = FontWeight.SemiBold
                            ); Text(
                            "${service.distanceKm} km • ★ ${service.rating} (${service.reviewCount})",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ); Text(
                            service.specialties.joinToString(" · "),
                            style = MaterialTheme.typography.bodySmall
                        )
                        }
                        StatusPill(
                            if (service.isOpenNow) "OPEN" else "CLOSED",
                            if (service.isOpenNow) androidx.compose.ui.graphics.Color(0xFF137333) else androidx.compose.ui.graphics.Color(
                                0xFF9A6500
                            )
                        )
                    }
                }
            }
        }
    }
}
