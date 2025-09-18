package com.iboplus.app.ui.servers

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.iboplus.app.data.model.PlaylistItem
import com.iboplus.app.ui.theme.IboPlusTheme
import com.iboplus.app.viewmodel.PanelViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * ServersActivity
 *
 * - Lista as playlists/servidores vindos do painel (playlist.php)
 * - Permite o usuário selecionar uma e "Conectar"
 * - Após conectar, a lista é recarregada no app (futuro: persistir escolha)
 */
@AndroidEntryPoint
class ServersActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            IboPlusTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ServersScreen(
                        onConnect = { /* TODO: acione a navegação/atualização desejada */ },
                        onBack = { finish() }
                    )
                }
            }
        }
    }
}

/* ------------------------------- UI -------------------------------- */

@Composable
private fun ServersScreen(
    onConnect: (PlaylistItem) -> Unit,
    onBack: () -> Unit
) {
    val vm: PanelViewModel = hiltViewModel()
    val context = LocalContext.current

    val playlists by vm.playlists.collectAsState()
    val appUser by vm.appUser.collectAsState()

    // Carrega dados iniciais se necessário
    LaunchedEffect(Unit) {
        val (mac, chave) = generateLocalCredentials(context)
        vm.loadInitialData(mac, chave)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Servidores / Playlist",
                style = MaterialTheme.typography.headlineSmall
            )
            OutlinedButton(onClick = onBack) { Text("Voltar") }
        }

        // Informações rápidas da conta
        appUser?.let { user ->
            Text(
                text = "MAC: ${user.mac ?: "--"} • Chave: ${user.chave ?: "--"} • Status: ${user.status ?: "--"}",
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Ações
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(onClick = {
                val (mac, chave) = generateLocalCredentials(context)
                vm.refreshPlaylists(mac, chave)
            }) { Text("Atualizar listas") }
        }

        // Listagem de servidores
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(
                items = playlists,
                key = { (it.id ?: it.name ?: it.url ?: it.hashCode().toString()).toString() }
            ) { item ->
                ServerRow(item, onConnect)
                Divider()
            }
        }
    }
}

@Composable
private fun ServerRow(
    item: PlaylistItem,
    onConnect: (PlaylistItem) -> Unit
) {
    val name = item.name ?: "Servidor"
    val type = item.type ?: "desconhecido"
    val active = item.isActive ?: false

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onConnect(item) }
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(text = name, style = MaterialTheme.typography.titleSmall)
            Text(
                text = "Tipo: $type • ${if (active) "Ativo" else "Inativo"}",
                style = MaterialTheme.typopiahy.bodySmall
            )
        }
        Button(onClick = { onConnect(item) }) {
            Text("Conectar")
        }
    }
}

/* ---------------------------- Helpers ---------------------------- */

private fun generateLocalCredentials(context: Context): Pair<String, String> {
    val androidId = android.provider.Settings.Secure.getString(
        context.contentResolver,
        android.provider.Settings.Secure.ANDROID_ID
    ) ?: "iboplus"
    val mac = md5(androidId).take(12).uppercase()
    val six = kotlin.math.abs(androidId.hashCode() % 1_000_000)
        .toString()
        .padStart(6, '0')
    return mac to six
}

private fun md5(input: String): String {
    val md = java.security.MessageDigest.getInstance("MD5")
    val bytes = md.digest(input.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}
