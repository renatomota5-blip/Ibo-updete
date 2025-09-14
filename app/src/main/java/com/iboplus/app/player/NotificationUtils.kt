package com.iboplus.app.player

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.iboplus.app.R

object NotificationUtils {
    const val CHANNEL_ID_MEDIA = "ibo_media"
    private const val CHANNEL_NAME_MEDIA = "Reprodução"

    fun ensureMediaChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mgr = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (mgr.getNotificationChannel(CHANNEL_ID_MEDIA) == null) {
                val ch = NotificationChannel(
                    CHANNEL_ID_MEDIA, CHANNEL_NAME_MEDIA, NotificationManager.IMPORTANCE_LOW
                ).apply {
                    setShowBadge(false)
                    description = "Notificações de reprodução em andamento"
                }
                mgr.createNotificationChannel(ch)
            }
        }
    }

    fun buildForegroundNotification(
        context: Context,
        title: String = "IBO Plus",
        text: String = "Reproduzindo…"
    ): Notification {
        ensureMediaChannel(context)
        return NotificationCompat.Builder(context, CHANNEL_ID_MEDIA)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(text)
            .setOngoing(true)
            .setSilent(true)
            .build()
    }
}
