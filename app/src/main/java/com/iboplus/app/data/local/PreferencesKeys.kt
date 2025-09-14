package com.iboplus.app.data.local

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

/**
 * Chaves centralizadas do DataStore.
 * Usadas pelo AppDataStore e também pelos ViewModels/Repos.
 */
object PreferencesKeys {
    val THEME = stringPreferencesKey("theme")              // claro/escuro ou tema do painel
    val SERVER_ID = stringPreferencesKey("server_id")      // último servidor conectado
    val LIVE_FORMAT = stringPreferencesKey("live_format")  // HLS/TS/MP4
    val EXTERNAL_PLAYER = booleanPreferencesKey("external_player")
    val AUTO_UPDATE = booleanPreferencesKey("auto_update")
    val USE_24H = booleanPreferencesKey("use_24h")
}
