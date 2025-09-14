package com.iboplus.app.data.parser

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
 * Parser centralizado usando Moshi.
 * Responsável por converter JSON ⇆ Models.
 */
object JsonParser {

    private val moshi: Moshi by lazy {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    /**
     * Retorna um adapter genérico para qualquer classe de modelo.
     */
    fun <T> adapter(clazz: Class<T>) = moshi.adapter(clazz)

    /**
     * Converte um JSON para o objeto de modelo.
     */
    fun <T> fromJson(json: String?, clazz: Class<T>): T? {
        return try {
            if (json.isNullOrBlank()) null else adapter(clazz).fromJson(json)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Converte um objeto em JSON.
     */
    fun <T> toJson(obj: T, clazz: Class<T>): String? {
        return try {
            adapter(clazz).toJson(obj)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
