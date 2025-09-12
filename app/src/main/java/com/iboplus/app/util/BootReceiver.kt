package com.iboplus.app.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.iboplus.app.ui.splash.SplashActivity

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            // Exemplo: apenas prepara algo; não iniciamos Activity automaticamente.
            // Se quiser iniciar após boot, descomente:
            // val i = Intent(context, SplashActivity::class.java)
            // i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            // context.startActivity(i)
        }
    }
}
