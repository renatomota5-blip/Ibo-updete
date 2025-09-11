
package com.iboplus.app.data

import android.content.Context

class Repo(private val ctx: Context) {
    private val prefs = Prefs(ctx)
    fun ensureIds() {
        if (prefs.mac.isBlank()) prefs.mac = DeviceId.generateMac()
        if (prefs.key.isBlank()) prefs.key = DeviceId.generateKey()
    }
    fun mac() = prefs.mac
    fun key() = prefs.key
}
