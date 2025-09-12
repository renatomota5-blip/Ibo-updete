package com.iboplus.app.ui.live

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.iboplus.app.R

class PlayerService : Service() {

    private val channelId = "player_service"

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, "Playback",
                NotificationManager.IMPORTANCE_LOW
            )
            val nm = getSystemService(NotificationManager::class.java)
            nm.createNotificationChannel(channel)
        }
        val notif: Notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("IBOPlus")
            .setContentText("Serviço de reprodução")
            .setSmallIcon(R.mipmap.ic_launcher)
            .build()
        startForeground(1, notif)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
