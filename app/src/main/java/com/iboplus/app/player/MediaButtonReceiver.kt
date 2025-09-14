package com.iboplus.app.player

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import com.google.android.exoplayer2.ExoPlayer

/**
 * Receiver para lidar com eventos de botões físicos de mídia
 * (play, pause, next, previous, headset button, controle remoto).
 */
class MediaButtonReceiver(
    private val player: ExoPlayer? = null
) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || intent.action != Intent.ACTION_MEDIA_BUTTON) return
        val keyEvent: KeyEvent? = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT)

        if (keyEvent?.action == KeyEvent.ACTION_DOWN) {
            when (keyEvent.keyCode) {
                KeyEvent.KEYCODE_MEDIA_PLAY -> player?.play()
                KeyEvent.KEYCODE_MEDIA_PAUSE -> player?.pause()
                KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE -> {
                    if (player?.isPlaying == true) player.pause() else player?.play()
                }
                KeyEvent.KEYCODE_MEDIA_NEXT -> player?.seekForward()
                KeyEvent.KEYCODE_MEDIA_PREVIOUS -> player?.seekBack()
            }
        }
    }
}
