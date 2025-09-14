package com.iboplus.app.viewmodel.model

/**
 * Modelos de UI para a tela de TV ao Vivo.
 * Serão preenchidos a partir do parser M3U/Xtream.
 */

/** Categoria de canais ao vivo (ex.: "Esportes", "Filmes", "Noticias"). */
data class LiveCategoryUi(
    val id: String,          // id estável (ex.: slug/normalized name)
    val name: String,        // rótulo exibido
    val count: Int = 0       // quantidade de canais na categoria
)

/** Canal ao vivo. */
data class LiveChannelUi(
    val id: String,          // id estável (url, id do painel, hash)
    val name: String,        // nome do canal
    val url: String,         // url direta do stream (m3u8/mpd/ts)
    val group: String? = null,   // nome da categoria/grupo original
    val logo: String? = null,    // url do logo se existir
    val number: String? = null   // número do canal, se disponível
)

/** Estado exposto pelo LiveViewModel. */
data class LiveUiState(
    val loading: Boolean = false,
    val error: String? = null,

    val categories: List<LiveCategoryUi> = emptyList(),
    val selectedCategoryId: String? = null,

    val channels: List<LiveChannelUi> = emptyList() // lista já filtrada por categoria/busca
)
```0
