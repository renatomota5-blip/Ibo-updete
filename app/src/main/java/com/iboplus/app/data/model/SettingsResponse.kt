package com.iboplus.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Representa a resposta do endpoint setting.json
 * Exemplo esperado:
 * {
 *   "app_name": "IBO Plus",
 *   "version": "3.8",
 *   "update_url": "https://...",
 *   "free_test_days": 7,
 *   "support_whatsapp": "5599999999999"
 * }
 */
data class SettingsResponse(

    @SerializedName("app_name")
    val appName: String? = null,

    @SerializedName("version")
    val version: String? = null,

    @SerializedName("update_url")
    val updateUrl: String? = null,

    @SerializedName("free_test_days")
    val freeTestDays: Int? = null,

    @SerializedName("support_whatsapp")
    val supportWhatsapp: String? = null
)
