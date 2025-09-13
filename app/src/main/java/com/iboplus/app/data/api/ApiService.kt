package com.iboplus.app.data.api

import com.iboplus.app.data.model.*
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Definição das chamadas às APIs do painel IBO Plus.
 * Base URL: https://iboplus.motanetplay.top/api/
 */
interface ApiService {

    // === Configurações globais ===
    @GET("setting.json")
    suspend fun getSettings(): SettingsResponse

    @GET("language.json")
    suspend fun getLanguages(): Map<String, String>

    @GET("note.json")
    suspend fun getNotes(): List<NoteMessage>

    @GET("logo.php")
    suspend fun getLogo(): LogoResponse

    @GET("bg.php")
    suspend fun getBackground(): BackgroundResponse

    @GET("qr.php")
    suspend fun getQr(): QrResponse

    // === Usuário/App ===
    @GET("getappuser.php")
    suspend fun getAppUser(
        @Query("mac") mac: String,
        @Query("chave") chave: String
    ): AppUserResponse

    // === Playlists / Servidores ===
    @GET("playlist.php")
    suspend fun getPlaylists(
        @Query("mac") mac: String,
        @Query("chave") chave: String
    ): List<PlaylistItem>

    // === Ads ===
    @GET("ads.php")
    suspend fun getAds(): List<AdItem>

    @GET("allads.php")
    suspend fun getAllAds(): List<AdItem>

    @GET("apiautoads.php")
    suspend fun getAutoAds(): List<AdItem>

    @GET("apiautoads2.php")
    suspend fun getAutoAds2(): List<AdItem>

    // === TMDB Backdrops ===
    @GET("backdrop.php")
    suspend fun getBackdrop(): BackdropResponse

    @GET("backdrop2.php")
    suspend fun getBackdrop2(): BackdropResponse

    // === Contato / Suporte ===
    @GET("contato.php")
    suspend fun getContato(): ContatoResponse

    // === Esportes (caso implementado no painel) ===
    @GET("sports.php")
    suspend fun getSports(): SportsResponse
}
