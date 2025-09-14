package com.iboplus.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Representa a resposta do endpoint sports.php
 *
 * Exemplo esperado:
 * {
 *   "events": [
 *     {
 *       "id": 101,
 *       "title": "Brasil x Argentina",
 *       "category": "Futebol",
 *       "time": "2025-09-20 21:00:00",
 *       "stream_url": "http://example.com/stream.m3u8",
 *       "logo": "http://example.com/logo.png"
 *     }
 *   ]
 * }
 */
data class SportsResponse(

    @SerializedName("events")
    val events: List<SportEvent>? = null
)

data class SportEvent(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("category")
    val category: String? = null,

    @SerializedName("time")
    val time: String? = null,

    @SerializedName("stream_url")
    val streamUrl: String? = null,

    @SerializedName("logo")
    val logo: String? = null
)
