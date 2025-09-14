package com.iboplus.app.viewmodel.model

/**
 * Modelos de UI para a tela de Séries.
 * Estrutura semelhante à de Filmes, mas com temporadas/episódios.
 */

/** Categoria de séries (ex.: "Drama", "Infantil"). */
data class SeriesCategoryUi(
    val id: String,
    val name: String,
    val count: Int = 0
)

/** Série principal. */
data class SeriesUi(
    val id: String,
    val title: String,
    val posterUrl: String? = null,
    val year: String? = null,
    val overview: String? = null
)

/** Temporada de uma série. */
data class SeasonUi(
    val id: String,
    val name: String,
    val number: Int,
    val episodes: List<EpisodeUi> = emptyList()
)

/** Episódio dentro de uma temporada. */
data class EpisodeUi(
    val id: String,
    val title: String,
    val number: Int,
    val seasonNumber: Int,
    val streamUrl: String? = null,
    val stillUrl: String? = null,
    val plot: String? = null
)

/** Estado da tela de Séries. */
data class SeriesUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val categories: List<SeriesCategoryUi> = emptyList(),
    val selectedCategoryId: String? = null,
    val items: List<SeriesUi> = emptyList()
)
