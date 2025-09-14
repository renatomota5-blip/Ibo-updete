package com.iboplus.app.data.repo

import com.iboplus.app.data.api.ApiService
import com.iboplus.app.data.parser.M3uParser
import com.iboplus.app.viewmodel.model.MovieCategoryUi
import com.iboplus.app.viewmodel.model.MovieUi
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repositório responsável por carregar os filmes (VOD).
 * Busca playlist específica de filmes e converte com M3uParser.
 */
@Singleton
class MoviesRepository @Inject constructor(
    private val api: ApiService
) {
    private var cacheMovies: List<MovieUi> = emptyList()
    private var cacheCats: List<MovieCategoryUi> = emptyList()

    /**
     * Busca lista de filmes no painel e faz parse.
     * O endpoint real pode ser `movies.php` ou outro mapeado no ApiService.
     */
    suspend fun refresh(): Pair<List<MovieCategoryUi>, List<MovieUi>> {
        val raw = api.getMoviesPlaylist()
        val entries = M3uParser.parse(raw)
        val (catNames, movies) = M3uParser.toMovies(entries)

        cacheCats = catNames.map { id -> MovieCategoryUi(id, id,
            movies.count { it.id.startsWith(id) || it.title.contains(id, ignoreCase = true) }) }
        cacheMovies = movies

        return cacheCats to cacheMovies
    }

    suspend fun getCategories(): List<MovieCategoryUi> {
        if (cacheCats.isEmpty()) refresh()
        return cacheCats
    }

    suspend fun getMovies(): List<MovieUi> {
        if (cacheMovies.isEmpty()) refresh()
        return cacheMovies
    }
}
