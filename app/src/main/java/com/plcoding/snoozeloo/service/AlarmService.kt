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


        when (intent?.action) {
            Actions.START_FOREGROUND_SERVICE.toString() -> {
                val alarmId = intent.getIntExtra("ALARM_ID", -1)
                if (alarmId == -1) throw Error("Alarm id is equal to -1, that's no good")
                startFullScreen(alarmId)
            }

            Actions.STOP_FOREGROUND_SERVICE.toString() -> stopSelf()
        }


        serviceScope.launch {
            delay(TimeUnit.MINUTES.toMillis(1))
            // TODO dodać reschedule of alarm
            stopSelf()
        }

        return START_STICKY
    }

    private fun startFullScreen(alarmId: Int) {
        // Play ringtone part remains the same
        dbScope.launch {
            try {
                val alarmById = alarmsDatabase.alarmsDao().getAlarmById(alarmId)
                val ringtoneUri = Uri.parse(alarmById.alarmRingtoneId)
                ringtonesManager.playRingtone(context = this@AlarmService, ringtoneUri)
            } catch (e: Exception) {
                stopSelf()
                return@launch
            }
        }

        // Create intents with proper flags
        val fullScreenIntent = Intent(this, LockScreenAlarmActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra("ALARM_ID", alarmId)
        }

        val fullScreenPendingIntent = PendingIntent.getActivity(
            this,
            alarmId,  // Use alarmId for unique pending intents
            fullScreenIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Create a high-priority notification that will show even when app is minimized
        val notification = NotificationCompat.Builder(this, "ALARM_SERVICE_CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Alarm")
            .setContentText("Alarm with id $alarmId is ringing!")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .setOngoing(true)  // Make it persistent
            .setAutoCancel(false)  // Prevent auto-cancellation
            .build()

        // Start the full screen activity explicitly
        startActivity(fullScreenIntent)

        // Start foreground with notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            startForeground(
                alarmId,  // Use alarmId as notification id
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_SYSTEM_EXEMPTED
            )
        } else {
            startForeground(alarmId, notification)
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