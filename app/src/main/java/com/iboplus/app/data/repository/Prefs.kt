package com.iboplus.app.data.repository

import android.content.Context
import android.content.SharedPreferences

/**
 * Wrapper para SharedPreferences do app.
 * Usado para salvar pequenas informações locais (ex.: login, flags).
 */
class Prefs(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("ibo_prefs", Context.MODE_PRIVATE)

    fun putString(key: String, value: String?) {
        prefs.edit().putString(key, value).apply()
    }

    fun getString(key: String, default: String? = null): String? {
        return prefs.getString(key, default)
    }

    fun putBoolean(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, default: Boolean = false): Boolean {
        return prefs.getBoolean(key, default)
    }

    fun putInt(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    fun getInt(key: String, default: Int = 0): Int {
        return prefs.getInt(key, default)
    }

    fun clear() {
        prefs.edit().clear().apply()
    }
}
