package com.iboplus.app.data.repo

import com.iboplus.app.data.api.ApiService
import com.iboplus.app.data.parser.M3uParser
import com.iboplus.app.viewmodel.model.SeriesCategoryUi
import com.iboplus.app.viewmodel.model.SeriesUi
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repositório responsável por carregar Séries (VOD em episódios).
 * Usa playlists do painel e faz parse com M3uParser.
 */
@Singleton
class SeriesRepository @Inject constructor(
    private val api: ApiService
) {
    private var cacheSeries: List<SeriesUi> = emptyList()
    private var cacheCats: List<SeriesCategoryUi> = emptyList()

    /**
     * Busca lista de séries no painel e faz parse.
     * Endpoint real pode ser `series.php` ou playlist específica.
     */
    suspend fun refresh(): Pair<List<SeriesCategoryUi>, List<SeriesUi>> {
        val raw = api.getSeriesPlaylist()
        val entries = M3uParser.parse(raw)
        val (catNames, series) = M3uParser.toSeries(entries)

        cacheCats = catNames.map { id ->
            SeriesCategoryUi(
                id = id,
                name = id,
                count = series.count { it.id.startsWith(id) || it.title.contains(id, ignoreCase = true) }
            )
        }
        cacheSeries = series

        return cacheCats to cacheSeries
    }

    suspend fun getCategories(): List<SeriesCategoryUi> {
        if (cacheCats.isEmpty()) refresh()
        return cacheCats
    }

    suspend fun getSeries(): List<SeriesUi> {
        if (cacheSeries.isEmpty()) refresh()
        return cacheSeries
    }
}
