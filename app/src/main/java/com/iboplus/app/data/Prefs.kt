package com.iboplus.app.data

import android.content.Context
import android.content.SharedPreferences

class Prefs(ctx: Context) {
    private val p: SharedPreferences =
        ctx.getSharedPreferences("iboplus", Context.MODE_PRIVATE)

    var mac: String
        get() = p.getString("mac", "") ?: ""
        set(v) = p.edit().putString("mac", v).apply()

    var key: String
        get() = p.getString("key", "") ?: ""
        set(v) = p.edit().putString("key", v).apply()

    var token: String
        get() = p.getString("token", "") ?: ""
        set(v) = p.edit().putString("token", v).apply()
}
