package com.iboplus.app.data.di

import com.iboplus.app.data.api.ApiService
import com.iboplus.app.data.repository.ApiClient
import com.iboplus.app.data.repository.Repo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo de injeção de dependências (Hilt).
 * Fornece instâncias únicas de Repo e ApiService.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService = ApiClient.service

    @Provides
    @Singleton
    fun provideRepo(api: ApiService): Repo = Repo(api)
}
