package com.iboplus.app.player

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.Player
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Wrapper simples para ExoPlayer. Sem código solto/top level.
 */
@Singleton
class ExoPlayerManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private var player: ExoPlayer? = null

    fun getOrCreate(): ExoPlayer {
        val existing = player
        if (existing != null) return existing
        return ExoPlayer.Builder(context).build().also { player = it }
    }

    fun prepare(url: String, playWhenReady: Boolean = true) {
        val exo = getOrCreate()
        val item = MediaItem.fromUri(Uri.parse(url))
        exo.setMediaItem(item)
        exo.prepare()
        exo.playWhenReady = playWhenReady
    }

    fun setListener(listener: Player.Listener?) {
        getOrCreate().apply {
            clearListener()
            listener?.let { addListener(it) }
        }
    }

    private fun ExoPlayer.clearListener() {
        // não guardamos referência; quem setou, gerencia o lifecycle
    }

    fun pause() {
        player?.pause()
    }

    fun stop() {
        player?.stop()
    }

    fun release() {
        player?.release()
        player = null
    }
}
