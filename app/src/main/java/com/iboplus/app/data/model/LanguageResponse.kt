package com.iboplus.app.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Modelo para o endpoint: GET language.json
 * Origem: painel IBO Plus (api/language.json)
 */
@JsonClass(generateAdapter = true)
data class LanguageResponse(
    @Json(name = "code") val code: String,
    @Json(name = "name") val name: String
)
