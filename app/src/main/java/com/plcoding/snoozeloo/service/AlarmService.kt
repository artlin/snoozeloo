package com.plcoding.snoozeloo.service

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.plcoding.snoozeloo.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AlarmService: Service() {

    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            Actions.START_FOREGROUND_SERVICE.toString() -> startFService()
            Actions.STOP_FOREGROUND_SERVICE.toString() -> stopSelf()
        }

        serviceScope.launch {
            delay(TimeUnit.MINUTES.toMillis(1))
            stopSelf()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun startFService() {
        val notification = NotificationCompat.Builder(this, "ALARM_SERVICE_CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Alarm")
            .setContentText("Alarm is running")
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            startForeground(1, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_SYSTEM_EXEMPTED)
        } else {
            startForeground(1, notification)
        }
    }

    override fun onDestroy() {
        serviceScope.cancel()
        super.onDestroy()
    }

    enum class Actions {
        START_FOREGROUND_SERVICE,
        STOP_FOREGROUND_SERVICE
    }
}