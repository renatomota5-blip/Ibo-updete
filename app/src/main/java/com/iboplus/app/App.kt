package com.iboplus.app

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // Lugar para init global (prefs, logs, etc)
    }

    companion object {
        fun ctx(app: Application): Context = app.applicationContext
    }
}
