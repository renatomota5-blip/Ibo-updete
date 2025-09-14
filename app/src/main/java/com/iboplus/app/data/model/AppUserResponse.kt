package com.iboplus.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Representa a resposta do endpoint getappuser.php
 *
 * Exemplo esperado:
 * {
 *   "status": "active",
 *   "mac": "A1B2C3D4E5F6",
 *   "chave": "123456",
 *   "is_active": true,
 *   "expires_at": "2025-09-30",
 *   "plan_name": "Plano Premium",
 *   "message": "Ativado com sucesso"
 * }
 */
data class AppUserResponse(

    @SerializedName("status")
    val status: String? = null, // active, inactive, expired...

    @SerializedName("mac")
    val mac: String? = null,

    @SerializedName("chave")
    val chave: String? = null,

    @SerializedName("is_active")
    val isActive: Boolean? = null,

    @SerializedName("expires_at")
    val expiresAt: String? = null,

    @SerializedName("plan_name")
    val planName: String? = null,

    @SerializedName("message")
    val message: String? = null
)
