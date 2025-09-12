package com.iboplus.app.data

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit

class ApiClient(
    private val endpoints: PanelEndpoints
) {
    private val client = OkHttpClient.Builder()
        .callTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    fun ping(): Result<String> = runCatching {
        val req = Request.Builder()
            .url(endpoints.ping)
            .get()
            .build()
        client.newCall(req).execute().use { it.body?.string().orEmpty() }
    }

    fun login(user: String, pass: String): Result<String> = runCatching {
        val json = """{"u":"$user","p":"$pass"}"""
        val body = json.toRequestBody("application/json".toMediaType())
        val req = Request.Builder().url(endpoints.login).post(body).build()
        client.newCall(req).execute().use { it.body?.string().orEmpty() }
    }
}
