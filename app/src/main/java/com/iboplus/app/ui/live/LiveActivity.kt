// app/src/main/java/com/iboplus/app/ui/live/LiveActivity.kt
package com.iboplus.app.ui.live

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.iboplus.app.data.model.PlaylistItem
import com.iboplus.app.player.MediaPlaybackService
import com.iboplus.app.ui.theme.IboPlusTheme
import com.iboplus.app.viewmodel.PanelViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LiveActivity : ComponentActivity() {

    private var mediaService: MediaPlaybackService? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            mediaService = (binder as? MediaPlaybackService.LocalBinder)?.getService()
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            mediaService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ensureMediaChannel()

        val intent = Intent(this, MediaPlaybackService::class.java)
        startService(intent)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)

        setContent {
            IboPlusTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LiveScreen(
                        onPlay = { url -> mediaService?.play(url) },
                        onStop = { mediaService?.stopPlayback() }
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        runCatching { unbindService(connection) }
        super.onDestroy()
    }

    private fun ensureMediaChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "media_playback"
            val mgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (mgr.getNotificationChannel(channelId) == null) {
                val ch = NotificationChannel(
                    channelId,
                    "Reprodução de mídia",
                    NotificationManager.IMPORTANCE_LOW
                )
                ch.setSound(null, null)
                mgr.createNotificationChannel(ch)
            }
        }
    }
}

/* ----------------------------- UI Compose ------------------------------ */

@Composable
private fun LiveScreen(
    onPlay: (String) -> Unit,
    onStop: () -> Unit
) {
    val context = LocalContext.current
    val vm: PanelViewModel = androidx.hilt.navigation.compose.hiltViewModel()

    val playlists by vm.playlists.collectAsState()
    val appUser by vm.appUser.collectAsState()

    var customUrl by remember { mutableStateOf("") }
    var playingUrl by remember { mutableStateOf<String?>(null) }

    // Carrega dados iniciais do painel
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
        Text(
            text = "TV ao Vivo",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold)
        )

        // Status da conta
        appUser?.let { user ->
            AssistChipRow(
                items = listOfNotNull(
                    "MAC: ${user.mac ?: "--"}",
                    "Chave: ${user.chave ?: "--"}",
                    "Status: ${user.status ?: "--"}",
                    user.expiresAt?.let { "Vence: $it" }
                )
            )
        }

        // URL manual
        OutlinedTextField(
            value = customUrl,
            onValueChange = { customUrl = it },
            label = { Text("URL direta (m3u8, m3u, etc.)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = {
                    if (customUrl.isNotBlank()) {
                        playingUrl = customUrl.trim()
                        onPlay(playingUrl!!)
                    }
                }
            ) { Text("Reproduzir URL") }

            OutlinedButton(onClick = {
                onStop()
                playingUrl = null
            }) { Text("Parar") }
        }

        Divider()

        // Lista de playlists
        Text("Playlists disponíveis", style = MaterialTheme.typography.titleMedium)
        if (playlists.isEmpty()) {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text("Nenhuma playlist recebida do painel.")
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(playlists, key = { (it.id ?: it.name ?: it.url ?: it.hashCode().toString()).toString() }) { item ->
                    PlaylistRow(item = item, onPlay = { url ->
                        playingUrl = url
                        onPlay(url)
                    })
                }
            }
        }

        if (playingUrl != null) {
            Text(
                text = "Reproduzindo: $playingUrl",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun PlaylistRow(
    item: PlaylistItem,
    onPlay: (String) -> Unit
) {
    val title = item.name ?: "Playlist"
    val subtitle = item.type ?: "desconhecido"
    val active = item.isActive ?: false
    val url = item.url ?: ""

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = url.isNotBlank()) { onPlay(url) }
            .padding(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(title, style = MaterialTheme.typography.titleSmall)
            AssistChip(
                onClick = {},
                enabled = false,
                label = { Text(if (active) "Ativo" else "Inativo") },
                colors = AssistChipDefaults.assistChipColors()
            )
        }
        Spacer(Modifier.height(4.dp))
        Text(subtitle, style = MaterialTheme.typography.bodySmall)
    }
}

/* ----------------------------- Helpers ----------------------------- */

@Composable
private fun AssistChipRow(items: List<String>) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items.forEach { text ->
            AssistChip(onClick = {}, enabled = false, label = { Text(text) })
        }
    }
}

private fun generateLocalCredentials(context: Context): Pair<String, String> {
    val androidId = android.provider.Settings.Secure.getString(
        context.contentResolver,
        android.provider.Settings.Secure.ANDROID_ID
    ) ?: "iboplus"
    val mac = md5(androidId).take(12).uppercase()
    val six = kotlin.math.abs(androidId.hashCode() % 1_000_000).toString().padStart(6, '0')
    return mac to six
}

private fun md5(input: String): String {
    val md = java.security.MessageDigest.getInstance("MD5")
    val bytes = md.digest(input.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}
