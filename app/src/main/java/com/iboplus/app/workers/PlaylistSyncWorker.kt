package com.iboplus.app.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.iboplus.app.data.repo.LiveRepository
import com.iboplus.app.data.repo.MoviesRepository
import com.iboplus.app.data.repo.SeriesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Worker para sincronizar playlists em background.
 * Roda periodicamente (ex.: 1x ao dia) se autoUpdate estiver habilitado.
 */
@HiltWorker
class PlaylistSyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val liveRepo: LiveRepository,
    private val moviesRepo: MoviesRepository,
    private val seriesRepo: SeriesRepository
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            liveRepo.refresh()
            moviesRepo.refresh()
            seriesRepo.refresh()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
