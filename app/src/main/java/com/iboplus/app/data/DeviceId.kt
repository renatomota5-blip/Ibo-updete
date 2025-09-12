package com.iboplus.app.data

import android.content.Context
import java.util.UUID

object DeviceId {
    fun getOrCreate(ctx: Context, prefs: Prefs): String {
        val current = prefs.deviceId
        if (!current.isNullOrBlank()) return current
        val fresh = UUID.randomUUID().toString()
        prefs.deviceId = fresh
        return fresh
    }
}
