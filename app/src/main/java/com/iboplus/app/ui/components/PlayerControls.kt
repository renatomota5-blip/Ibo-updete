package com.iboplus.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Controles de player de mídia (ExoPlayer).
 * Exibidos sobre o vídeo (overlay).
 */
@Composable
fun PlayerControls(
    isPlaying: Boolean,
    position: Long,
    duration: Long,
    onPlayPause: () -> Unit,
    onStop: () -> Unit,
    onSeek: (Long) -> Unit,
    onToggleFullscreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.85f))
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        ColumnWithSlider(
            isPlaying = isPlaying,
            position = position,
            duration = duration,
            onPlayPause = onPlayPause,
            onStop = onStop,
            onSeek = onSeek,
            onToggleFullscreen = onToggleFullscreen
        )
    }
}

@Composable
private fun ColumnWithSlider(
    isPlaying: Boolean,
    position: Long,
    duration: Long,
    onPlayPause: () -> Unit,
    onStop: () -> Unit,
    onSeek: (Long) -> Unit,
    onToggleFullscreen: () -> Unit
) {
    // Barra de progresso
    Slider(
        value = if (duration > 0) position.toFloat() / duration.toFloat() else 0f,
        onValueChange = { fraction ->
            val newPos = (duration * fraction).toLong()
            onSeek(newPos)
        },
        modifier = Modifier.fillMaxWidth()
    )

    // Botões principais
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Replay10,
            contentDescription = "Retroceder 10s",
            modifier = Modifier.clickable {
                val seekBack = (position - 10_000).coerceAtLeast(0)
                onSeek(seekBack)
            }
        )
        Icon(
            imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
            contentDescription = if (isPlaying) "Pausar" else "Reproduzir",
            modifier = Modifier.clickable { onPlayPause() }
        )
        Icon(
            imageVector = Icons.Filled.Stop,
            contentDescription = "Parar",
            modifier = Modifier.clickable { onStop() }
        )
        Icon(
            imageVector = Icons.Filled.Fullscreen,
            contentDescription = "Tela cheia",
            modifier = Modifier.clickable { onToggleFullscreen() }
        )
    }

    // Texto com posição/tempo total
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = formatTime(position),
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            text = formatTime(duration),
            style = MaterialTheme.typography.labelSmall
        )
    }
}

private fun formatTime(ms: Long): String {
    val totalSeconds = ms / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%02d:%02d".format(minutes, seconds)
}
