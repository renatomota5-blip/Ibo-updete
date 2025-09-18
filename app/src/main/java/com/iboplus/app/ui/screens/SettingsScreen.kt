package com.iboplus.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.iboplus.app.viewmodel.SettingsViewModel
import com.iboplus.app.viewmodel.model.LiveFormat
import com.iboplus.app.viewmodel.model.SettingsUi

/**
 * Tela "Configurar"
 */
@Composable
fun SettingsScreen(
    vm: SettingsViewModel,
    onOpenServers: () -> Unit,
    onOpenLanguage: () -> Unit,
    onAddPlaylist: () -> Unit,
    onChangeLayout: () -> Unit,
    onSelectDeviceType: () -> Unit,
) {
    val ui by vm.state.collectAsState()

    LaunchedEffect(Unit) { vm.load() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text(
            text = "Configurar",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(Modifier.height(8.dp))

        when {
            ui.loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                androidx.compose.material3.CircularProgressIndicator()
            }
            ui.error != null -> ErrorBox(
                message = ui.error ?: "Erro ao carregar configurações",
                onRetry = { vm.load() }
            )
            else -> {
                SettingsList(
                    ui = ui,
                    vm = vm,
                    onOpenServers = onOpenServers,
                    onOpenLanguage = onOpenLanguage,
                    onAddPlaylist = onAddPlaylist,
                    onChangeLayout = onChangeLayout,
                    onSelectDeviceType = onSelectDeviceType
                )

                Spacer(Modifier.height(18.dp))
                Divider()
                Spacer(Modifier.height(10.dp))

                Text(
                    text = "Endereço MAC: ${ui.mac}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun SettingsList(
    ui: SettingsUi,
    vm: SettingsViewModel,
    onOpenServers: () -> Unit,
    onOpenLanguage: () -> Unit,
    onAddPlaylist: () -> Unit,
    onChangeLayout: () -> Unit,
    onSelectDeviceType: () -> Unit,
) {
    val items = buildList<@Composable () -> Unit> {
        add {
            ActionRow(
                title = "adicionar lista de reprodução",
                subtitle = "Inserir/colar URL M3U / Xtream ou scannear QR",
                onClick = onAddPlaylist
            )
        }
        add {
            ActionRow(
                title = "Controle dos Pais",
                subtitle = if (ui.parentalEnabled) "Ativado" else "Desativado",
                onClick = { vm.toggleParentalDialog() }
            )
        }
        add { ActionRow(title = "Servidores", subtitle = "Gerenciar playlists", onClick = onOpenServers) }
        add { ActionRow(title = "mudar idioma", onClick = onOpenLanguage) }
        add { ActionRow(title = "Alterar layout", onClick = onChangeLayout) }

        add {
            SwitchRow(
                title = "Ocultar Categorias ao Vivo",
                checked = ui.hideLiveCats,
                onCheckedChange = { vm.setHideLiveCats(it) }
            )
        }
        add {
            SwitchRow(
                title = "Ocultar Categorias Vod",
                checked = ui.hideVodCats,
                onCheckedChange = { vm.setHideVodCats(it) }
            )
        }
        add {
            SwitchRow(
                title = "Ocultar Categorias Series",
                checked = ui.hideSeriesCats,
                onCheckedChange = { vm.setHideSeriesCats(it) }
            )
        }

        add {
            ActionRow(
                title = "Limpar histórico de filmes",
                subtitle = "Remove itens vistos recentemente",
                onClick = { vm.clearMoviesHistory() }
            )
        }
        add {
            SwitchRow(
                title = "Não há filmes vistos recentemente",
                checked = ui.disableRecentlyWatched,
                onCheckedChange = { vm.setDisableRecentlyWatched(it) }
            )
        }

        add {
            // Live Stream Format
            DropdownRow(
                title = "Live Stream Format",
                selected = when (ui.liveFormat) {
                    LiveFormat.HLS -> "HLS (.m3u8)"
                    LiveFormat.TS -> "MPEG-TS (.ts)"
                    LiveFormat.MP4 -> "MP4"
                },
                options = listOf("HLS (.m3u8)", "MPEG-TS (.ts)", "MP4"),
                onSelect = { label ->
                    val new = when (label) {
                        "MP4" -> LiveFormat.MP4
                        "MPEG-TS (.ts)" -> LiveFormat.TS
                        else -> LiveFormat.HLS
                    }
                    vm.setLiveFormat(new)
                }
            )
        }

        add {
            SwitchRow(
                title = "jogador externo",
                checked = ui.externalPlayer,
                onCheckedChange = { vm.setExternalPlayer(it) }
            )
        }

        add {
            SwitchRow(
                title = "automática (atualização da lista)",
                checked = ui.autoUpdateList,
                onCheckedChange = { vm.setAutoUpdate(it) }
            )
        }

        add {
            DropdownRow(
                title = "Formato da hora",
                selected = if (ui.use24h) "24 horas" else "12 horas",
                options = listOf("24 horas", "12 horas"),
                onSelect = { vm.setUse24h(it == "24 horas") }
            )
        }

        add {
            ActionRow(
                title = "Configurações de legenda",
                subtitle = ui.subtitleSummary,
                onClick = { vm.openSubtitleDialog() }
            )
        }

        add {
            ActionRow(
                title = "Select Device Type",
                subtitle = ui.deviceTypeLabel,
                onClick = onSelectDeviceType
            )
        }

        add {
            ButtonRow(
                title = "atualize agora",
                onClick = { vm.forceNowUpdate() }
            )
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(items) { it() }
    }
}

/* -------------------------- Rows -------------------------- */

@Composable
private fun ActionRow(
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit
) {
    Surface(
        tonalElevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            if (!subtitle.isNullOrBlank()) {
                Spacer(Modifier.height(4.dp))
                Text(subtitle, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun SwitchRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Surface(tonalElevation = 1.dp, modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Switch(checked = checked, onCheckedChange = onCheckedChange)
        }
    }
}

@Composable
private fun DropdownRow(
    title: String,
    selected: String,
    options: List<String>,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Surface(tonalElevation = 1.dp, modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(6.dp))
            OutlinedButton(onClick = { expanded = true }) {
                Text(selected)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { opt ->
                    DropdownMenuItem(
                        text = { Text(opt) },
                        onClick = {
                            expanded = false
                            onSelect(opt)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ButtonRow(
    title: String,
    onClick: () -> Unit
) {
    Surface(tonalElevation = 1.dp, modifier = Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = onClick) { Text(title) }
        }
    }
}

/* ---------------------- Util de erro simples ---------------------- */

@Composable
private fun ErrorBox(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(message, style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(8.dp))
        OutlinedButton(onClick = onRetry) { Text("Tentar novamente") }
    }
}
