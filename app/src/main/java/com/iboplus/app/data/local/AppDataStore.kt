package com.iboplus.app.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * DataStore para salvar preferências locais do app.
 * Ex.: tema, servidor selecionado, configurações do player.
 */

private const val STORE_NAME = "ibo_plus_prefs"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = STORE_NAME)

object PrefKeys {
    val THEME = stringPreferencesKey("theme")              // claro/escuro ou tema do painel
    val SERVER_ID = stringPreferencesKey("server_id")      // último servidor conectado
    val LIVE_FORMAT = stringPreferencesKey("live_format")  // HLS/TS/MP4
    val EXTERNAL_PLAYER = booleanPreferencesKey("external_player")
    val AUTO_UPDATE = booleanPreferencesKey("auto_update")
    val USE_24H = booleanPreferencesKey("use_24h")
}

class AppDataStore(private val context: Context) {

    val theme: Flow<String?> = context.dataStore.data.map { it[PrefKeys.THEME] }
    val serverId: Flow<String?> = context.dataStore.data.map { it[PrefKeys.SERVER_ID] }
    val liveFormat: Flow<String?> = context.dataStore.data.map { it[PrefKeys.LIVE_FORMAT] }
    val externalPlayer: Flow<Boolean> = context.dataStore.data.map { it[PrefKeys.EXTERNAL_PLAYER] ?: false }
    val autoUpdate: Flow<Boolean> = context.dataStore.data.map { it[PrefKeys.AUTO_UPDATE] ?: true }
    val use24h: Flow<Boolean> = context.dataStore.data.map { it[PrefKeys.USE_24H] ?: true }

    suspend fun setTheme(value: String) {
        context.dataStore.edit { it[PrefKeys.THEME] = value }
    }

    suspend fun setServerId(value: String) {
        context.dataStore.edit { it[PrefKeys.SERVER_ID] = value }
    }

    suspend fun setLiveFormat(value: String) {
        context.dataStore.edit { it[PrefKeys.LIVE_FORMAT] = value }
    }

    suspend fun setExternalPlayer(enabled: Boolean) {
        context.dataStore.edit { it[PrefKeys.EXTERNAL_PLAYER] = enabled }
    }

    suspend fun setAutoUpdate(enabled: Boolean) {
        context.dataStore.edit { it[PrefKeys.AUTO_UPDATE] = enabled }
    }

    suspend fun setUse24h(enabled: Boolean) {
        context.dataStore.edit { it[PrefKeys.USE_24H] = enabled }
    }
}
