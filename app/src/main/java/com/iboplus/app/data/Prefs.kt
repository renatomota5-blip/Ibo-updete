package com.iboplus.app.data

import android.content.Context
import androidx.core.content.edit

class Prefs(ctx: Context) {
    private val sp = ctx.getSharedPreferences("iboplus_prefs", Context.MODE_PRIVATE)

    var painelUrl: String?
        get() = sp.getString("painel_url", null)
        set(v) = sp.edit { putString("painel_url", v) }

    var deviceId: String?
        get() = sp.getString("device_id", null)
        set(v) = sp.edit { putString("device_id", v) }
}
