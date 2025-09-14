package com.iboplus.app.data.repository

import com.iboplus.app.data.api.ApiService
import com.iboplus.app.data.model.*

/**
 * Camada de repositório que centraliza chamadas à API.
 * Todas as telas/ViewModels usam esse wrapper.
 */
class Repo(private val api: ApiService = ApiClient.service) {

    suspend fun getSettings(): SettingsResponse? =
        safeCall { api.getSettings() }

    suspend fun getLanguages(): List<LanguageResponse> =
        safeCall { api.getLanguages() } ?: emptyList()

    suspend fun getNotes(): List<NoteResponse> =
        safeCall { api.getNotes() } ?: emptyList()

    suspend fun getLogo(): LogoResponse? =
        safeCall { api.getLogo() }

    suspend fun getBackground(): BackgroundResponse? =
        safeCall { api.getBackground() }

    suspend fun getBackdrop(): BackdropResponse? =
        safeCall { api.getBackdrop() }

    suspend fun getBackdrop2(): BackdropResponse? =
        safeCall { api.getBackdrop2() }

    suspend fun getQr(): QrResponse? =
        safeCall { api.getQr() }

    suspend fun getContact(): ContatoResponse? =
        safeCall { api.getContact() }

    suspend fun getPlaylist(): List<PlaylistItem> =
        safeCall { api.getPlaylist() } ?: emptyList()

    suspend fun getSports(): SportsResponse? =
        safeCall { api.getSports() }

    suspend fun getAds(): AdsResponse? =
        safeCall { api.getAds() }

    suspend fun getAllAds(): AdsResponse? =
        safeCall { api.getAllAds() }

    suspend fun getApiAutoAds(): AdsResponse? =
        safeCall { api.getApiAutoAds() }

    suspend fun getApiAutoAds2(): AdsResponse? =
        safeCall { api.getApiAutoAds2() }

    suspend fun getAppUser(): AppUserResponse? =
        safeCall { api.getAppUser() }

    suspend fun getIntro(): NoteMessage? =
        safeCall { api.getIntro() }

    suspend fun getIndex(): NoteMessage? =
        safeCall { api.getIndex() }

    suspend fun getSettingPhp(): SettingsResponse? =
        safeCall { api.getSettingPhp() }

    /**
     * Envolve chamada de rede com tratamento de erro genérico.
     */
    private suspend fun <T> safeCall(block: suspend () -> T): T? {
        return try {
            block()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
