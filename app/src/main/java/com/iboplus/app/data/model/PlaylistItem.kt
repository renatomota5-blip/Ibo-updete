package com.iboplus.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Representa um item de playlist/servidor do endpoint playlist.php
 *
 * Exemplo esperado:
 * [
 *   {
 *     "id": 1,
 *     "name": "Servidor Principal",
 *     "type": "m3u",
 *     "url": "http://example.com/lista.m3u",
 *     "is_active": true
 *   },
 *   {
 *     "id": 2,
 *     "name": "Servidor Backup",
 *     "type": "xtream",
 *     "url": "http://example.com:8080",
 *     "is_active": false
 *   }
 * ]
 */
data class PlaylistItem(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("type")
    val type: String? = null, // m3u, xtream, stalker, etc.

    @SerializedName("url")
    val url: String? = null,

    @SerializedName("is_active")
    val isActive: Boolean? = null
)
