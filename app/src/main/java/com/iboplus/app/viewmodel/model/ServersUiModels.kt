package com.iboplus.app.viewmodel.model

/**
 * Modelos de UI para a tela de Servidores / Playlist.
 * Representam a lista de servidores configurados no painel.
 */

/** Item de servidor/playlist. */
data class ServerItemUi(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val connected: Boolean = false
)

/** QR Code exibido na tela de servidores. */
data class QrUi(
    val imageUrl: String,
    val title: String? = null,
    val subtitle: String? = null,
    val webUrl: String? = null,
    val onOpen: (() -> Unit)? = null
)

/** Estado da tela de servidores. */
data class ServersUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val items: List<ServerItemUi> = emptyList(),
    val qr: QrUi? = null,
    val mac: String = "",
    val deviceKey: String = "",
    val statusText: String? = null
)
