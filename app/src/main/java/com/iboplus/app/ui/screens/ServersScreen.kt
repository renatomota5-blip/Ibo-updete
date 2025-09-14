package com.iboplus.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.iboplus.app.viewmodel.ServersViewModel
import com.iboplus.app.viewmodel.model.QrUi
import com.iboplus.app.viewmodel.model.ServerItemUi

/**
 * Tela "Servidores / Playlist"
 * - Lista de playlists disponíveis (quando houver > 1)
 * - Botões para Conectar/Desconectar
 * - Mostra QR configurado no painel
 * - Mostra MAC + Device Key (para suporte/ativação)
 */
@Composable
fun ServersScreen(
    vm: ServersViewModel,
    onConnected: () -> Unit = {}
) {
    val ui by vm.state.collectAsState()

    LaunchedEffect(Unit) { vm.load() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Servidores / Playlist",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )

        when {
            ui.loading -> LoadingBox()

            ui.error != null -> ErrorBox(
                message = ui.error ?: "Erro ao carregar servidores",
                onRetry = { vm.load() }
            )

            else -> {
                if (ui.qr != null) {
                    QrCard(ui.qr!!)
                }

                DeviceInfoCard(
                    mac = ui.mac,
                    deviceKey = ui.deviceKey,
                    statusText = ui.statusText
                )

                if (ui.items.isEmpty()) {
                    EmptyServers()
                } else {
                    Text(
                        text = "Playlists disponíveis",
                        style = MaterialTheme.typography.titleMedium
                    )
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(items = ui.items, key = { it.id }) { item ->
                            ServerRow(
                                item = item,
                                onConnect = {
                                    vm.connect(item.id) { ok ->
                                        if (ok) onConnected()
                                    }
                                }
                            )
                        }
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedButton(onClick = { vm.reloadFromPanel() }) {
                        Text("Recarregar listas")
                    }
                    Button(onClick = {
                        vm.disconnect()
                    }) {
                        Text("Desconectar")
                    }
                }
            }
        }
    }
}

/* ---------------- Components ---------------- */

@Composable
private fun LoadingBox() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyServers() {
    Surface(
        tonalElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Nenhuma playlist encontrada",
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Peça ao vendedor para adicionar a lista usando seu MAC e Chave do dispositivo."
            )
        }
    }
}

@Composable
private fun QrCard(qr: QrUi) {
    Surface(
        tonalElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(qr.imageUrl),
                contentDescription = "QR",
                modifier = Modifier.size(96.dp),
                contentScale = ContentScale.Crop
            )
            Column(Modifier.weight(1f)) {
                Text(
                    text = qr.title ?: "Escaneie o QRCode",
                    fontWeight = FontWeight.SemiBold
                )
                if (!qr.subtitle.isNullOrBlank()) {
                    Spacer(Modifier.height(4.dp))
                    Text(qr.subtitle!!)
                }
            }
            if (!qr.webUrl.isNullOrBlank()) {
                AssistChip(onClick = { qr.onOpen?.invoke() }, label = { Text("Abrir site") })
            }
        }
    }
}

@Composable
private fun DeviceInfoCard(
    mac: String,
    deviceKey: String,
    statusText: String?
) {
    Surface(
        tonalElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Informações do dispositivo", fontWeight = FontWeight.SemiBold)
            InfoRow("Endereço MAC", mac)
            InfoRow("Chave do dispositivo", deviceKey)
            if (!statusText.isNullOrBlank()) {
                InfoRow("Estado", statusText)
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

@Composable
private fun ServerRow(
    item: ServerItemUi,
    onConnect: () -> Unit
) {
    Surface(
        tonalElevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onConnect() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                if (!item.subtitle.isNullOrBlank()) {
                    Spacer(Modifier.height(2.dp))
                    Text(item.subtitle!!, style = MaterialTheme.typography.bodySmall)
                }
                if (item.connected) {
                    Spacer(Modifier.height(6.dp))
                    AssistChip(
                        onClick = {},
                        label = { Text("Connected") },
                        enabled = false
                    )
                }
            }
            Button(onClick = onConnect) {
                Text(if (item.connected) "Reconectar" else "Conectar")
            }
        }
    }
}
```0
