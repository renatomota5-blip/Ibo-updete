package com.iboplus.app.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Cliente Retrofit configurado para o painel IBO Plus.
 * Base URL: https://iboplus.motanetplay.top/api/
 */
object ApiClient {

    private const val BASE_URL = "https://iboplus.motanetplay.top/api/"

    // Configuração do OkHttp
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    // Configuração do Retrofit
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Serviço pronto para uso
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
