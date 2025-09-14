package com.iboplus.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Representa uma mensagem do endpoint note.json
 * Exemplo esperado:
 * [
 *   { "title": "Aviso", "message": "Manutenção às 23h" },
 *   { "title": "Promoção", "message": "Convide amigos e ganhe bônus" }
 * ]
 */
data class NoteMessage(

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("message")
    val message: String? = null
)
