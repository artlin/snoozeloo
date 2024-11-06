package com.plcoding.snoozeloo.core.domain

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androix.startup.KoinStartup.onKoinStartup
import org.koin.core.logger.Level

class SnoozelooApplication: Application() {

    init {
        onKoinStartup {
            androidLogger(Level.ERROR)
            androidContext(this@SnoozelooApplication)
            modules()
        }
    }

    override fun onCreate() {
        super.onCreate()
    }

}