package com.iboplus.app.viewmodel.model

/**
 * Modelos de UI para a tela de Configurações.
 * Representam opções ajustáveis pelo usuário.
 */

/** Formato de streaming ao vivo. */
enum class LiveFormat {
    HLS,   // .m3u8
    TS,    // .ts
    MP4    // .mp4
}

/** Estado da tela de Configurações. */
data class SettingsUi(
    val loading: Boolean = false,
    val error: String? = null,

    val parentalEnabled: Boolean = false,
    val hideLiveCats: Boolean = false,
    val hideVodCats: Boolean = false,
    val hideSeriesCats: Boolean = false,
    val disableRecentlyWatched: Boolean = false,

    val liveFormat: LiveFormat = LiveFormat.HLS,
    val externalPlayer: Boolean = false,
    val autoUpdateList: Boolean = true,
    val use24h: Boolean = true,

    val subtitleSummary: String = "Padrão",
    val deviceTypeLabel: String = "TV",

    val mac: String = ""
)
