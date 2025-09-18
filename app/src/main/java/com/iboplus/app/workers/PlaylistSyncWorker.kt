package com.iboplus.app.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.Result
import androidx.work.WorkerParameters

class PlaylistSyncWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            val playlistUrl = inputData.getString(KEY_PLAYLIST_URL)

            // TODO: implemente sua sincronização real (rede/BD).
            if (playlistUrl.isNullOrBlank()) {
                Result.success()
            } else {
                Result.success(
                    Data.Builder()
                        .putString(KEY_RESULT, "synced:$playlistUrl")
                        .build()
                )
            }
        } catch (_: Throwable) {
            Result.retry()
        }
    }

    companion object {
        const val NAME = "PlaylistSyncWorker"
        const val KEY_PLAYLIST_URL = "playlist_url"
        const val KEY_RESULT = "playlist_result"
    }
}
