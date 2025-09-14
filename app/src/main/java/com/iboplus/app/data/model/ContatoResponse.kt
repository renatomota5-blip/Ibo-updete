package com.iboplus.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Representa a resposta do endpoint contato.php
 *
 * Exemplo esperado:
 * {
 *   "whatsapp": "5599999999999",
 *   "email": "suporte@iboplus.com",
 *   "site": "https://iboplus.motanetplay.top",
 *   "telegram": "@iboplus"
 * }
 */
data class ContatoResponse(

    @SerializedName("whatsapp")
    val whatsapp: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("site")
    val site: String? = null,

    @SerializedName("telegram")
    val telegram: String? = null
)
