package com.iboplus.app.data.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.iboplus.app.data.model.AdItem
import com.iboplus.app.data.model.AppUserResponse
import com.iboplus.app.data.model.BackdropResponse
import com.iboplus.app.data.model.BackgroundResponse
import com.iboplus.app.data.model.LogoResponse
import com.iboplus.app.data.model.NoteMessage
import com.iboplus.app.data.model.PlaylistItem
import com.iboplus.app.data.model.QrResponse
import com.iboplus.app.data.model.SettingsResponse
import com.iboplus.app.data.model.SportsResponse

/**
 * Resultado padronizado para chamadas do repositório.
 */
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String, val cause: Throwable? = null) : Result<Nothing>()
}

/**
 * Contrato do repositório que fala com o “painel”.
 * No momento, mantemos uma implementação fake para compilar.
 */
interface PanelRepository {

    suspend fun fetchSettings(): Result<SettingsResponse?>
    suspend fun fetchLogo(): Result<LogoResponse?>
    suspend fun fetchBackground(): Result<BackgroundResponse?>
    suspend fun fetchQr(): Result<QrResponse?>
    suspend fun fetchNotes(): Result<List<NoteMessage>?>
    suspend fun fetchAppUser(mac: String, chave: String): Result<AppUserResponse?>
    suspend fun fetchPlaylists(mac: String, chave: String): Result<List<PlaylistItem>?>
    suspend fun fetchAds(): Result<List<AdItem>?>
    suspend fun fetchBackdrop(): Result<BackdropResponse?>
    suspend fun fetchSports(): Result<SportsResponse?>
}

/**
 * Implementação “stub” para permitir o build enquanto a camada de rede não está conectada.
 * Retorna valores vazios/óbvios sem falhar.
 */
@Singleton
class PanelRepositoryImpl @Inject constructor() : PanelRepository {

    override suspend fun fetchSettings(): Result<SettingsResponse?> =
        Result.Success(null)

    override suspend fun fetchLogo(): Result<LogoResponse?> =
        Result.Success(null)

    override suspend fun fetchBackground(): Result<BackgroundResponse?> =
        Result.Success(null)

    override suspend fun fetchQr(): Result<QrResponse?> =
        Result.Success(null)

    override suspend fun fetchNotes(): Result<List<NoteMessage>?> =
        Result.Success(emptyList())

    override suspend fun fetchAppUser(mac: String, chave: String): Result<AppUserResponse?> =
        Result.Success(null)

    override suspend fun fetchPlaylists(mac: String, chave: String): Result<List<PlaylistItem>?> =
        Result.Success(emptyList())

    override suspend fun fetchAds(): Result<List<AdItem>?> =
        Result.Success(emptyList())

    override suspend fun fetchBackdrop(): Result<BackdropResponse?> =
        Result.Success(null)

    override suspend fun fetchSports(): Result<SportsResponse?> =
        Result.Success(null)
}
