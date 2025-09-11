package com.iboplus.app.data

import android.content.Context

class Repo(private val ctx: Context) {
    private val prefs = Prefs(ctx)

    /** Gera e salva MAC/KEY se ainda não existirem */
    fun ensureIds() {
        if (prefs.mac.isBlank()) prefs.mac = DeviceId.generateMac()
        if (prefs.key.isBlank()) prefs.key = DeviceId.generateKey()
    }

    fun mac(): String = prefs.mac
    fun key(): String = prefs.key
}
