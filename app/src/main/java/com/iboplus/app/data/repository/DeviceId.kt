package com.iboplus.app.data.repository

import android.content.Context
import android.provider.Settings
import java.util.UUID

/**
 * Utilitário para gerar/obter um identificador único do dispositivo.
 * Esse ID é usado pelo painel para associar app instalado ao cliente.
 */
object DeviceId {

    private const val PREF_KEY = "device_id"

    fun get(context: Context): String {
        val prefs = context.getSharedPreferences("ibo_prefs", Context.MODE_PRIVATE)

        // Já existe salvo?
        prefs.getString(PREF_KEY, null)?.let { return it }

        // Caso contrário, gerar novo
        val androidId = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )

        val generated = if (androidId.isNullOrBlank()) {
            UUID.randomUUID().toString()
        } else {
            androidId
        }

        prefs.edit().putString(PREF_KEY, generated).apply()
        return generated
    }
}
