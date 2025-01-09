package com.plcoding.snoozeloo.core.domain

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.plcoding.snoozeloo.di.ringtoneListModule
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
                    coreModule,
                    navigationModule,
                    alarmManagerModule,
                    repositoryModule,
                    schedulerModule,
                    ringtoneListModule
                )
            )
        }

        val channel = NotificationChannel(
            "ALARM_SERVICE_CHANNEL_ID",
            "Alarm Notifications",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Alarm notifications"
            enableLights(true)
            enableVibration(true)
            setShowBadge(true)
            setBypassDnd(true)  // Bypass Do Not Disturb
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }
}