package com.iboplus.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Representa a resposta do endpoint bg.php
 * Pode ser uma URL de imagem ou um código de cor (hex).
 *
 * Exemplos possíveis:
 * { "type": "image", "value": "https://iboplus.motanetplay.top/uploads/bg.jpg" }
 * { "type": "color", "value": "#000000" }
 */
data class BackgroundResponse(

    @SerializedName("type")
    val type: String? = null, // "image" ou "color"

    @SerializedName("value")
    val value: String? = null // URL ou código de cor
)
