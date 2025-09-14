package com.iboplus.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.iboplus.app.viewmodel.PanelViewModel

/**
 * Tela "Conta"
 * Mostra informações básicas do usuário obtidas do painel:
 * - MAC
 * - DeviceKey
 * - Status (Ativo/Expirado)
 * - Data de Expiração
 * - Nome do Plano
 */
@Composable
fun AccountScreen(
    vm: PanelViewModel
) {
    val state by vm.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Minha Conta",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )

        if (!state.initialLoaded) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    InfoRow("Endereço MAC", state.settings?.mac ?: "—")
                    InfoRow("Chave do Dispositivo", state.settings?.deviceKey ?: "—")
                    InfoRow("Plano", state.settings?.planName ?: "—")
                    InfoRow("Expira em", state.settings?.expiryDate ?: "—")
                    InfoRow(
                        "Status",
                        if (state.hasValidPlaylist) "Ativo ✅" else "Inativo ⏳"
                    )
                }
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        Text(
            value,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}
