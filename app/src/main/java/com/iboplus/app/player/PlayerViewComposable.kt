package com.iboplus.app.player

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView

/**
 * Composable para exibir o ExoPlayer dentro do Jetpack Compose.
 * Utiliza o ExoPlayerManager como fonte do player.
 *
 * Exemplo de uso:
 *
 * val manager = remember { ExoPlayerManager(context) }
 * PlayerViewComposable(
 *     modifier = Modifier.fillMaxSize(),
 *     playerManager = manager
 * )
 */
@Composable
fun PlayerViewComposable(
    modifier: Modifier = Modifier,
    playerManager: ExoPlayerManager
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            PlayerView(context).apply {
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                useController = true
                setShowNextButton(false)
                setShowPreviousButton(false)
                player = playerManager.player
            }
        },
        update = { view ->
            view.player = playerManager.player
        }
    )

    // Libera player quando o Composable sair de cena
    DisposableEffect(Unit) {
        onDispose {
            playerManager.release()
        }
    }
}
