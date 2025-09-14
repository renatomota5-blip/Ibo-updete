package com.iboplus.app.data.repo

import com.iboplus.app.data.api.ApiService
import com.iboplus.app.data.parser.M3uParser
import com.iboplus.app.viewmodel.model.LiveCategoryUi
import com.iboplus.app.viewmodel.model.LiveChannelUi
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repositório responsável por carregar canais de TV ao vivo.
 * Busca playlists do painel via ApiService e converte com M3uParser.
 */
@Singleton
class LiveRepository @Inject constructor(
    private val api: ApiService
) {
    private var cacheChannels: List<LiveChannelUi> = emptyList()
    private var cacheCats: List<LiveCategoryUi> = emptyList()

    /**
     * Busca lista do painel e faz parse.
     * O endpoint real pode ser `playlist.php` ou outro mapeado no ApiService.
     */
    suspend fun refresh(): Pair<List<LiveCategoryUi>, List<LiveChannelUi>> {
        // Aqui usamos playlist.php (ajustar conforme API do painel)
        val raw = api.getPlaylist()
        val entries = M3uParser.parse(raw)
        val (cats, chans) = M3uParser.toLive(entries)
        cacheCats = cats
        cacheChannels = chans
        return cats to chans
    }

    suspend fun getCategories(): List<LiveCategoryUi> {
        if (cacheCats.isEmpty()) refresh()
        return cacheCats
    }

    suspend fun getChannels(): List<LiveChannelUi> {
        if (cacheChannels.isEmpty()) refresh()
        return cacheChannels
    }
}
