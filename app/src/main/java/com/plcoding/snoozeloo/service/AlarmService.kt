package com.plcoding.snoozeloo.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.net.Uri
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.plcoding.snoozeloo.R
import com.plcoding.snoozeloo.alarm_selection.presentation.RingtonesManager
import com.plcoding.snoozeloo.core.domain.LockScreenAlarmActivity
import com.plcoding.snoozeloo.core.domain.db.AlarmsDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.TimeUnit

class AlarmService : Service(), KoinComponent {

    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val dbScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val ringtonesManager: RingtonesManager by inject()
    private val alarmsDatabase: AlarmsDatabase by inject()

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val alarmId = intent?.getIntExtra("ALARM_ID", -1) ?: run {
            throw Error("Alarm id is equal to -1, that's no good")
        }

        when (intent.action) {
            Actions.START_FOREGROUND_SERVICE.toString() -> startFullScreen(alarmId)

            Actions.STOP_FOREGROUND_SERVICE.toString() -> stopSelf()
        }
        dbScope.launch {
            val alarmById = alarmsDatabase.alarmsDao().getAlarmById(alarmId)
            val ringtoneUri = Uri.parse(alarmById.alarmRingtoneId)
            ringtonesManager.playRingtone(context = this@AlarmService, ringtoneUri)
        }

        serviceScope.launch {
            delay(TimeUnit.MINUTES.toMillis(1))
            // TODO dodać reschedule of alarm
            stopSelf()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun startFullScreen(alarmId: Int) {
        val alarmScreenIntent = Intent(this, LockScreenAlarmActivity::class.java)
        alarmScreenIntent.putExtra("ALARM_ID", alarmId)

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            alarmScreenIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, "ALARM_SERVICE_CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Alarm")
            .setContentText("Alarm with id $alarmId is running")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setChannelId("ALARM_SERVICE_CHANNEL_ID")
            .setFullScreenIntent(
                pendingIntent,
                true
            )
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