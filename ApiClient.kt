
package com.iboplus.app.data

import okhttp3.OkHttpClient
import okhttp3.Request

object ApiClient {
    private val client = OkHttpClient.Builder().build()

    fun get(url: String, userAgent: String = defaultUA()): String {
        val req = Request.Builder().url(url)
            .header("User-Agent", userAgent)
            .build()
        client.newCall(req).execute().use { resp ->
            if (!resp.isSuccessful) error("HTTP " + resp.code)
            return resp.body?.string() ?: ""
        }
    }

    fun defaultUA(): String = "IBOPlus/1.0 (Android)"
}
