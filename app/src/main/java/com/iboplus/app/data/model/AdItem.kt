package com.iboplus.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Representa um anúncio (ads.php, allads.php, apiautoads.php, apiautoads2.php)
 *
 * Exemplo esperado:
 * [
 *   {
 *     "id": 1,
 *     "title": "Promoção Especial",
 *     "image": "https://iboplus.motanetplay.top/uploads/ads/promo.png",
 *     "link": "https://wa.me/5599999999999",
 *     "type": "banner"
 *   }
 * ]
 */
data class AdItem(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("image")
    val image: String? = null,

    @SerializedName("link")
    val link: String? = null,

    @SerializedName("type")
    val type: String? = null // banner, video, etc.
)
