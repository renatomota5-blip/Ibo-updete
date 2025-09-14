package com.iboplus.app.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Modelo para o endpoint: GET note.json
 * Origem: painel IBO Plus (api/note.json)
 */
@JsonClass(generateAdapter = true)
data class NoteResponse(
    @Json(name = "id") val id: String,
    @Json(name = "title") val title: String,
    @Json(name = "message") val message: String,
    @Json(name = "type") val type: String? = null, // info, warning, error, etc.
    @Json(name = "visible") val visible: Boolean = true
)
