package com.iboplus.app.ui.components

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView

/**
 * Componente de vídeo usando ExoPlayer.
 * Reproduz streams ao vivo, filmes e séries.
 */
@Composable
fun VideoPlayer(
    url: String,
    modifier: Modifier = Modifier,
    autoPlay: Boolean = true,
    onPlayerReady: ((ExoPlayer) -> Unit)? = null
) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(url))
            prepare()
            playWhenReady = autoPlay
        }
    }

    DisposableEffect(
        AndroidView(
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = true
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            },
            modifier = modifier.fillMaxSize()
        )
    ) {
        onPlayerReady?.invoke(exoPlayer)

        onDispose {
            exoPlayer.release()
        }
    }
}

/**
 * Wrapper que combina Player com controles customizados.
 */
@Composable
fun VideoPlayerWithControls(
    url: String,
    modifier: Modifier = Modifier,
    autoPlay: Boolean = true
) {
    var isPlaying by remember { mutableStateOf(autoPlay) }
    var position by remember { mutableStateOf(0L) }
    var duration by remember { mutableStateOf(0L) }

    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(url))
            prepare()
            playWhenReady = autoPlay
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        AndroidView(
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = false // usamos controles customizados
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        PlayerControls(
            isPlaying = isPlaying,
            position = position,
            duration = duration,
            onPlayPause = {
                if (exoPlayer.isPlaying) {
                    exoPlayer.pause()
                    isPlaying = false
                } else {
                    exoPlayer.play()
                    isPlaying = true
                }
            },
            onStop = {
                exoPlayer.stop()
                isPlaying = false
            },
            onSeek = { newPos ->
                exoPlayer.seekTo(newPos)
                position = newPos
            },
            onToggleFullscreen = {
                // TODO: implementar lógica de fullscreen (Activity flags)
            }
        )
    }

    DisposableEffect(Unit) {
        val listener = object : com.google.android.exoplayer2.Player.Listener {
            override fun onEvents(
                player: com.google.android.exoplayer2.Player,
                events: com.google.android.exoplayer2.Player.Events
            ) {
                position = player.currentPosition
                duration = player.duration.coerceAtLeast(0L)
                isPlaying = player.isPlaying
            }
        }
        exoPlayer.addListener(listener)

        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }
}
