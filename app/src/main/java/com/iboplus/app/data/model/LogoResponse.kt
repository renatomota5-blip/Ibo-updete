package com.iboplus.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Resposta de logo.php
 * Ex.: { "url": "https://.../uploads/logo.png" }
 */
data class LogoResponse(
    @SerializedName("url")
    val url: String? = null
)
