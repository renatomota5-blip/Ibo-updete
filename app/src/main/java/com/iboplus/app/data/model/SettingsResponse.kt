package com.iboplus.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Resposta de setting.json
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
