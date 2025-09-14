package com.iboplus.app.viewmodel.model

/**
 * Modelos de UI para a tela de Filmes.
 * São mapeados a partir das APIs/playlist (ex.: Xtream / TMDB).
 */

/** Categoria de filmes (ex.: "Ação", "Comédia"). */
data class MovieCategoryUi(
    val id: String,
    val name: String,
    val count: Int = 0
)

/** Filme individual. */
data class MovieUi(
    val id: String,
    val title: String,
    val posterUrl: String? = null,
    val year: String? = null,
    val plot: String? = null,
    val url: String? = null
)

/** Estado da tela de Filmes. */
data class MoviesUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val categories: List<MovieCategoryUi> = emptyList(),
    val selectedCategoryId: String? = null,
    val items: List<MovieUi> = emptyList()
)
