package com.iboplus.app.data.parser

import android.content.Context
import android.util.Log
import com.iboplus.app.data.model.SettingsResponse
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Carregador de configuração local (assets/config.json, se existir).
 * Serve como fallback inicial antes de chamar a API remota.
 */
object ConfigLoader {

    private const val CONFIG_FILE = "config.json"

    fun init(context: Context): SettingsResponse? {
        return try {
            val input = context.assets.open(CONFIG_FILE)
            val reader = BufferedReader(InputStreamReader(input))
            val json = reader.readText()
            reader.close()

            JsonParser.fromJson(json, SettingsResponse::class.java).also {
                Log.d("IBOPlus", "Config local carregada com sucesso")
            }
        } catch (e: Exception) {
            Log.e("IBOPlus", "Erro ao carregar config local", e)
            null
        }
    }
}
