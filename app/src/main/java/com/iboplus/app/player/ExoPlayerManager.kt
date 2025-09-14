package com.iboplus.app.player

import android.content.Context
import android.net.Uri
import androidx.annotation.MainThread
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector

/**
 * Gerencia uma instância única do ExoPlayer para reprodução de Live/VOD.
 * - Play por URL (HLS/DASH/MP4)
 * - Controle básico (pause, stop, seek)
 * - Liberação segura
 *
 * Dependências no build.gradle:
 *   implementation("androidx.media3:media3-exoplayer:<versão>")
 *   implementation("androidx.media3:media3-ui:<versão>")
 */
class ExoPlayerManager(context: Context) {

    private val trackSelector = DefaultTrackSelector(context).apply {
        // Ajustes padrão; pode ser customizado (legendas, áudio, resolução, etc.)
        setParameters(buildUponParameters())
    }

    val player: ExoPlayer = ExoPlayer
        .Builder(context)
        .setTrackSelector(trackSelector)
        .build()

    /**
     * Inicia a reprodução da URL informada.
     * @param url Suporta HLS (.m3u8), DASH (.mpd) e MP4.
     * @param autoplay Inicia tocando automaticamente se true.
     */
    @MainThread
    fun play(
        url: String,
        autoplay: Boolean = true
    ) {
        val mediaItem = MediaItem.Builder()
            .setUri(Uri.parse(url))
            .setMimeType(guessMimeType(url))
            .setMediaId(url)
            .build()

        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = autoplay
    }

    /** Pausa a reprodução. */
    @MainThread
    fun pause() {
        player.playWhenReady = false
    }

    /** Retoma a reprodução. */
    @MainThread
    fun resume() {
        player.playWhenReady = true
    }

    /** Para e limpa a fila. */
    @MainThread
    fun stop() {
        player.stop()
        player.clearMediaItems()
    }

    /** Faz seek em milissegundos. */
    @MainThread
    fun seekTo(positionMs: Long) {
        player.seekTo(positionMs)
    }

    /** Libera recursos do player — chamar ao destruir a tela. */
    @MainThread
    fun release() {
        player.release()
    }

    private fun guessMimeType(url: String): String? = when {
        url.endsWith(".m3u8", ignoreCase = true) -> MimeTypes.APPLICATION_M3U8
        url.endsWith(".mpd", ignoreCase = true) -> MimeTypes.APPLICATION_MPD
        url.endsWith(".mp4", ignoreCase = true) -> MimeTypes.VIDEO_MP4
        else -> null // Deixa o ExoPlayer inferir
    }
}
```0
