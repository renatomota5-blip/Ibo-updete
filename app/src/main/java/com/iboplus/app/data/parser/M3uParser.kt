package com.iboplus.app.data.parser

import com.iboplus.app.viewmodel.model.LiveChannelUi
import com.iboplus.app.viewmodel.model.LiveCategoryUi
import com.iboplus.app.viewmodel.model.MovieUi
import com.iboplus.app.viewmodel.model.SeriesUi

/**
 * Parser simples para listas M3U/M3U8.
 * Suporta canais de TV, filmes e séries.
 *
 * Exemplo de linha:
 *   #EXTINF:-1 tvg-id="123" tvg-name="HBO" group-title="Filmes",HBO
 *   http://servidor/stream/123.m3u8
 */
object M3uParser {

    data class Entry(
        val name: String,
        val url: String,
        val group: String? = null,
        val logo: String? = null,
        val extra: Map<String, String> = emptyMap()
    )

    /**
     * Faz parse bruto de um M3U em texto.
     */
    fun parse(raw: String): List<Entry> {
        val lines = raw.lines()
        val result = mutableListOf<Entry>()

        var currentName: String? = null
        var currentGroup: String? = null
        var currentLogo: String? = null
        var extras: MutableMap<String, String> = mutableMapOf()

        for (line in lines) {
            when {
                line.startsWith("#EXTINF", ignoreCase = true) -> {
                    currentName = null
                    currentGroup = null
                    currentLogo = null
                    extras = mutableMapOf()

                    // exemplo: #EXTINF:-1 tvg-id="123" tvg-logo="logo.png" group-title="Filmes",HBO
                    val parts = line.substringAfter(":").split(",")
                    if (parts.size >= 2) {
                        val attrs = parts[0]
                        currentName = parts[1].trim()

                        Regex("(\\w+)=\"([^\"]*)\"").findAll(attrs).forEach { m ->
                            val key = m.groupValues[1]
                            val value = m.groupValues[2]
                            when (key.lowercase()) {
                                "tvg-logo" -> currentLogo = value
                                "group-title" -> currentGroup = value
                                else -> extras[key] = value
                            }
                        }
                    } else if (parts.isNotEmpty()) {
                        currentName = parts.last().trim()
                    }
                }
                line.startsWith("#") -> {
                    // ignorar outros comentários
                }
                line.isNotBlank() -> {
                    val url = line.trim()
                    if (currentName != null) {
                        result.add(
                            Entry(
                                name = currentName!!,
                                url = url,
                                group = currentGroup,
                                logo = currentLogo,
                                extra = extras.toMap()
                            )
                        )
                    }
                }
            }
        }
        return result
    }

    /**
     * Converte entries para UI Models de canais ao vivo.
     */
    fun toLive(entries: List<Entry>): Pair<List<LiveCategoryUi>, List<LiveChannelUi>> {
        val categories = entries.groupBy { it.group ?: "Outros" }
            .map { (group, chans) ->
                LiveCategoryUi(
                    id = group,
                    name = group,
                    count = chans.size
                )
            }
        val channels = entries.mapIndexed { i, e ->
            LiveChannelUi(
                id = "live_$i",
                name = e.name,
                url = e.url,
                group = e.group,
                logo = e.logo,
                number = e.extra["tvg-id"]
            )
        }
        return categories to channels
    }

    /**
     * Converte entries para filmes.
     * Aqui assumimos que cada entry é um filme.
     */
    fun toMovies(entries: List<Entry>): Pair<List<String>, List<MovieUi>> {
        val categories = entries.mapNotNull { it.group }.distinct()
        val movies = entries.mapIndexed { i, e ->
            MovieUi(
                id = "movie_$i",
                title = e.name,
                posterUrl = e.logo,
                year = e.extra["year"],
                plot = e.extra["plot"],
                url = e.url
            )
        }
        return categories to movies
    }

    /**
     * Converte entries para séries.
     * Neste exemplo simples, não montamos temporadas/episódios.
     */
    fun toSeries(entries: List<Entry>): Pair<List<String>, List<SeriesUi>> {
        val categories = entries.mapNotNull { it.group }.distinct()
        val series = entries.mapIndexed { i, e ->
            SeriesUi(
                id = "series_$i",
                title = e.name,
                posterUrl = e.logo,
                year = e.extra["year"],
                overview = e.extra["plot"]
            )
        }
        return categories to series
    }
}
