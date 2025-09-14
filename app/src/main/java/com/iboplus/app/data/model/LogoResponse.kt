package com.iboplus.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Representa a resposta do endpoint logo.php
 * Exemplo esperado:
 * {
 *   "url": "https://iboplus.motanetplay.top/uploads/logo.png"
 * }
 */
data class LogoResponse(

    @SerializedName("url")
    val url: String? = null
)
