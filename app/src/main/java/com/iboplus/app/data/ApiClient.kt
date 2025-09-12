package com.iboplus.app.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/** Cliente Retrofit singleton, dado um baseUrl */
object ApiClient {
    @Volatile private var retrofit: Retrofit? = null

    fun get(baseUrl: String): Retrofit {
        val normalized = if (baseUrl.endsWith("/")) baseUrl else "$baseUrl/"
        val built = Retrofit.Builder()
            .baseUrl(normalized)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit = built
        return built
    }
}

/** API mínima para compilar (ping e login). Ajuste endpoints se necessário. */
interface ApiService {
    @GET("ping")
    suspend fun ping(): Map<String, Any>

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Map<String, Any>
}
