package com.plcoding.snoozeloo.core.domain

import android.app.Application
import com.plcoding.snoozeloo.di.alarmManagerModule
import com.plcoding.snoozeloo.di.coreModule
import com.plcoding.snoozeloo.di.navigationModule
import com.plcoding.snoozeloo.di.repositoryModule
import com.plcoding.snoozeloo.di.schedulerModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class SnoozelooApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@SnoozelooApplication)
            modules(
                listOf(
                    navigationModule,
                    coreModule,
                    alarmManagerModule,
                    repositoryModule,
                    schedulerModule
                )
            )
        }
    }
}