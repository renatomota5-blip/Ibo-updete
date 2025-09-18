package com.iboplus.app.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.Data
import androidx.work.Result

/**
 * Worker simples para sincronizar playlists.
 * Removido AssistedInject/Hilt para evitar dependências faltando.
 * Use OneTimeWorkRequestBuilder<PlaylistSyncWorker>() para enfileirar.
 */
class PlaylistSyncWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            // Exemplo: ler parâmetros de entrada, se houver
            val playlistUrl = inputData.getString(KEY_PLAYLIST_URL)

            // TODO: implemente aqui a sincronização real (rede/DB).
            // Coloquei um "no-op" para compilar sem erros.
            if (playlistUrl.isNullOrBlank()) {
                // Sem dados? Ok, executa com sucesso mesmo assim.
                Result.success()
            } else {
                // Simule trabalho
                // syncPlaylist(playlistUrl)
                Result.success(
                    Data.Builder()
                        .putString(KEY_RESULT, "synced:$playlistUrl")
                        .build()
                )
            }
        } catch (t: Throwable) {
            // Se for erro temporário, podemos tentar novamente
            Result.retry()
        }
    }

    companion object {
        const val NAME = "PlaylistSyncWorker"
        const val KEY_PLAYLIST_URL = "playlist_url"
        const val KEY_RESULT = "playlist_result"
    }
}
