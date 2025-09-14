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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.iboplus.app.data.model.PlaylistItem
import com.iboplus.app.player.MediaPlaybackService
import com.iboplus.app.ui.theme.IboPlusTheme
import com.iboplus.app.viewmodel.PanelViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * LiveActivity
 * - Lista servidores/playlists vindos do painel
 * - Permite conectar em um stream e reproduzir via MediaPlaybackService (ExoPlayer)
 * - Cria o NotificationChannel "media_playback" se necessário (Android 8+)
 */
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

        // Garante o canal de notificação usado pelo serviço
        ensureMediaChannel()

        // Inicia e vincula ao serviço de reprodução
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

    // Estados observados do ViewModel
    val playlists by vm.playlists.collectAsState()
    val appUser by vm.appUser.collectAsState()

    // Estados locais
    var customUrl by remember { mutableStateOf("") }
    var playingUrl by remember { mutableStateOf<String?>(null) }

    // Carrega dados iniciais do painel (MAC/CHAVE virão do dispositivo)
    LaunchedEffect(Unit) {
        // Geração simples; substitua por helpers reais (ActivationScreen usa os mesmos)
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

        // Status da conta (quando disponível)
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

        // Campo para URL direta (debug / manual)
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
