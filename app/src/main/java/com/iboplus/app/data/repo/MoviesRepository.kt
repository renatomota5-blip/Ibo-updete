package com.iboplus.app.data.repo

import com.iboplus.app.data.local.AppDataStore
import com.iboplus.app.data.local.PreferencesKeys
import com.iboplus.app.data.repository.PanelRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repositório fino para o domínio "Movies".
 */
@Singleton
class MoviesRepository @Inject constructor(
    private val panelRepository: PanelRepository,
    private val dataStore: AppDataStore
) {
    suspend fun refresh() {
        val mac = dataStore.readString(PreferencesKeys.MAC).firstOrNull()
        val key = dataStore.readString(PreferencesKeys.KEY).firstOrNull()
        if (!mac.isNullOrBlank() && !key.isNullOrBlank()) {
            panelRepository.fetchPlaylists(mac, key) // no futuro: endpoint específico p/ filmes
        }
    }
}
