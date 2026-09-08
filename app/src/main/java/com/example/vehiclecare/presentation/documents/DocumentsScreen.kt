package com.example.vehiclecare.presentation.documents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vehiclecare.core.common.UiLoadState
import com.example.vehiclecare.R
import com.example.vehiclecare.presentation.components.StatusPill
import com.example.vehiclecare.presentation.components.formatShort
import com.example.vehiclecare.presentation.navigation.AppDestination
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Composable fun DocumentsScreen(state: DocumentsUiState, onEvent: (DocumentsUiEvent) -> Unit, onNavigate: (AppDestination) -> Unit) {
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { Text(stringResource(R.string.documents), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold) }
        item { Text("Expiry monitoring for insurance, inspection and registration.", color = MaterialTheme.colorScheme.onSurfaceVariant) }
        when (val content = state.documents) {
            UiLoadState.Loading -> item { Text("Loading documents…") }
            is UiLoadState.Error -> item { Text("Could not load documents") }
            is UiLoadState.Success -> items(content.data) { document ->
                val days = ChronoUnit.DAYS.between(LocalDate.now(), document.expiresAt)
                Card(colors = CardDefaults.cardColors(containerColor = if (days < 30) Color(0xFFFFEDEA) else MaterialTheme.colorScheme.surfaceVariant)) {
                    Column(Modifier.padding(16.dp)) {
                        Row { Text(document.title, Modifier.weight(1f), fontWeight = FontWeight.SemiBold); StatusPill(if (days < 30) "${days} DAYS" else "ACTIVE", if (days < 30) Color(0xFFBA1A1A) else Color(0xFF137333)) }
                        Text("Expires ${document.expiresAt.formatShort()} • ${document.type.name.lowercase().replaceFirstChar(Char::titlecase)}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}
