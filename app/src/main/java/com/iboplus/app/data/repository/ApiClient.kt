package com.iboplus.app.data.repository

import android.util.Log
import com.iboplus.app.data.api.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Cliente HTTP central do app.
 * Responsável por instanciar o Retrofit + OkHttp para chamadas ao painel IBO Plus.
 *
 * Base URL definida como: https://iboplus.motanetplay.top/api/
 */
object ApiClient {

    private const val BASE_URL = "https://iboplus.motanetplay.top/api/"

    private val loggingInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor { message -> Log.d("ApiClient", message) }
            .apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val service: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
