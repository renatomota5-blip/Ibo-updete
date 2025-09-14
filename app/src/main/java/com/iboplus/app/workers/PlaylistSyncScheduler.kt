package com.iboplus.app.workers

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

/**
 * Agenda/cancela o PlaylistSyncWorker como um trabalho periódico único.
 */
object PlaylistSyncScheduler {

    private const val UNIQUE_WORK_NAME = "playlist_sync_worker"

    fun scheduleDaily(context: Context, repeatIntervalHours: Long = 24L) {
        val request = PeriodicWorkRequestBuilder<PlaylistSyncWorker>(
            repeatIntervalHours, TimeUnit.HOURS
        ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            UNIQUE_WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

    fun cancel(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(UNIQUE_WORK_NAME)
    }
}
