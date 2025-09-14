package com.iboplus.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Representa a resposta dos endpoints backdrop.php e backdrop2.php
 * Dados do TMDB usados para exibir banners e planos de fundo no app.
 *
 * Exemplo esperado:
 * {
 *   "results": [
 *     {
 *       "id": 123,
 *       "title": "Exemplo de Filme",
 *       "backdrop_path": "/path/to/image.jpg",
 *       "poster_path": "/path/to/poster.jpg",
 *       "overview": "Descrição do filme",
 *       "media_type": "movie"
 *     }
 *   ]
 * }
 */
data class BackdropResponse(

    @SerializedName("results")
    val results: List<BackdropItem>? = null
)

data class BackdropItem(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("name")
    val name: String? = null, // para séries

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("overview")
    val overview: String? = null,

    @SerializedName("media_type")
    val mediaType: String? = null // movie, tv
)
