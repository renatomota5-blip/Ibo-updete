package com.iboplus.app.data.repository

import com.iboplus.app.data.api.ApiClient
import com.iboplus.app.data.model.*

/**
 * Camada de acesso aos dados do painel IBO Plus.
 * Centraliza chamadas ao ApiService com tratamento de erro padronizado.
 */
class PanelRepository(
    private val service: com.iboplus.app.data.api.ApiService = ApiClient.apiService
) {

    /* --------------------------- Helpers --------------------------- */

    sealed class Result<out T> {
        data class Success<T>(val data: T) : Result<T>()
        data class Error(val message: String, val cause: Throwable? = null) : Result<Nothing>()
    }

    private inline fun <T> wrap(block: () -> T): Result<T> =
        try {
            Result.Success(block())
        } catch (t: Throwable) {
            Result.Error(t.message ?: "Erro desconhecido", t)
        }

    /* ----------------------- Configurações/Visual ------------------ */

    suspend fun fetchSettings(): Result<SettingsResponse> =
        wrap { service.getSettings() }

    suspend fun fetchLanguages(): Result<Map<String, String>> =
        wrap { service.getLanguages() }

    suspend fun fetchNotes(): Result<List<NoteMessage>> =
        wrap { service.getNotes() }

    suspend fun fetchLogo(): Result<LogoResponse> =
        wrap { service.getLogo() }

    suspend fun fetchBackground(): Result<BackgroundResponse> =
        wrap { service.getBackground() }

    suspend fun fetchQr(): Result<QrResponse> =
        wrap { service.getQr() }

    /* ---------------------------- Usuário -------------------------- */

    suspend fun fetchAppUser(mac: String, chave: String): Result<AppUserResponse> =
        wrap { service.getAppUser(mac = mac, chave = chave) }

    /* --------------------------- Playlists ------------------------- */

    suspend fun fetchPlaylists(mac: String, chave: String): Result<List<PlaylistItem>> =
        wrap { service.getPlaylists(mac = mac, chave = chave) }

    /* ----------------------------- Ads ----------------------------- */

    suspend fun fetchAds(): Result<List<AdItem>> =
        wrap { service.getAds() }

    suspend fun fetchAllAds(): Result<List<AdItem>> =
        wrap { service.getAllAds() }

    suspend fun fetchAutoAds(): Result<List<AdItem>> =
        wrap { service.getAutoAds() }

    suspend fun fetchAutoAds2(): Result<List<AdItem>> =
        wrap { service.getAutoAds2() }

    /* ---------------------------- TMDB ----------------------------- */

    suspend fun fetchBackdrop(): Result<BackdropResponse> =
        wrap { service.getBackdrop() }

    suspend fun fetchBackdrop2(): Result<BackdropResponse> =
        wrap { service.getBackdrop2() }

    /* --------------------------- Contato --------------------------- */

    suspend fun fetchContato(): Result<ContatoResponse> =
        wrap { service.getContato() }

    /* --------------------------- Esportes -------------------------- */

    suspend fun fetchSports(): Result<SportsResponse> =
        wrap { service.getSports() }
}
```0
