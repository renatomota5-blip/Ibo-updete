package com.iboplus.app.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

class PlaylistSyncWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): ListenableWorker.Result {
        return try {
            val playlistUrl = inputData.getString(KEY_PLAYLIST_URL)
            if (playlistUrl.isNullOrBlank()) {
                ListenableWorker.Result.success()
            } else {
                val output = Data.Builder()
                    .putString(KEY_RESULT, "synced:$playlistUrl")
                    .build()
                ListenableWorker.Result.success(output)
            }
        } catch (_: Throwable) {
            ListenableWorker.Result.retry()
        }
    }

    companion object {
        const val NAME = "PlaylistSyncWorker"
        const val KEY_PLAYLIST_URL = "playlist_url"
        const val KEY_RESULT = "playlist_result"
    }
}
