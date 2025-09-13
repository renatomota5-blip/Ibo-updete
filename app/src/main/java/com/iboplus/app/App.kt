package com.iboplus.app

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        // ===== Logging =====
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.d("IBO Plus App iniciado (debug mode)")
        }

        // ===== Pré-carregar configs do painel =====
        // Aqui poderíamos disparar uma chamada inicial para pegar logo/bg/tema
        try {
            ConfigLoader.init(this)
        } catch (e: Exception) {
            Log.e("IBOPlus", "Erro ao inicializar ConfigLoader", e)
        }

        // ===== Exceções globais =====
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            Log.e("IBOPlus", "Erro fatal em thread: ${t.name}", e)
        }
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(Log.INFO)
            .build()
    }
}
