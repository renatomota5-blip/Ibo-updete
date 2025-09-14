package com.iboplus.app.player

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.iboplus.app.R
import com.iboplus.app.ui.MainActivity

/**
 * Serviço em foreground para reprodução de mídia (ExoPlayer).
 * Garante que o player continue rodando mesmo em segundo plano.
 */
class MediaPlaybackService : LifecycleService() {

    private val binder = LocalBinder()
    private var player: ExoPlayer? = null

    inner class LocalBinder : Binder() {
        fun getService(): MediaPlaybackService = this@MediaPlaybackService
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        player = ExoPlayer.Builder(this).build()
    }

    override fun onDestroy() {
        player?.release()
        player = null
        super.onDestroy()
    }

    fun play(url: String) {
        val mediaItem = MediaItem.fromUri(url)
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.play()
        startForeground(1, buildNotification("Reproduzindo mídia"))
    }

    fun stopPlayback() {
        player?.stop()
        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    private fun buildNotification(contentText: String): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, "media_playback")
            .setContentTitle("IBO Plus")
            .setContentText(contentText)
            .setSmallIcon(R.drawable.ic_play)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }

    fun getPlayer(): ExoPlayer? = player
}
