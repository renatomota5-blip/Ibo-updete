package com.iboplus.app.data

import android.content.Context
import androidx.preference.PreferenceManager

/** Centraliza a URL do painel e cria o serviço da API. */
object PanelEndpoints {
    // chave usada nas Preferências (prefs.xml) para salvar a URL do painel
    const val KEY_PANEL_URL = "pref_panel_url"
    // fallback caso o usuário não tenha configurado
    const val DEFAULT_PANEL_URL = "https://iboplus.motanetplay.top/"

    /** Lê a URL do painel das SharedPreferences. */
    fun getPanelUrl(context: Context): String {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(KEY_PANEL_URL, DEFAULT_PANEL_URL) ?: DEFAULT_PANEL_URL
    }

    /** Instância do Retrofit ApiService apontando para a URL do painel. */
    fun api(context: Context): ApiService {
        val url = getPanelUrl(context)
        return ApiClient.get(url).create(ApiService::class.java)
    }
}
