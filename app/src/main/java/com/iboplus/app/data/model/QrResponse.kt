package com.iboplus.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Representa a resposta do endpoint qr.php
 * Exemplo esperado:
 * {
 *   "qr_url": "https://iboplus.motanetplay.top/uploads/qr.png",
 *   "redirect": "https://wa.me/5599999999999"
 * }
 */
data class QrResponse(

    @SerializedName("qr_url")
    val qrUrl: String? = null,

    @SerializedName("redirect")
    val redirect: String? = null
)
