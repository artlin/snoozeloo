package com.plcoding.snoozeloo.service

import android.Manifest
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ServiceInfo
import android.net.Uri
import android.os.Build
import android.os.IBinder
import androidx.compose.ui.text.intl.Locale
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.plcoding.snoozeloo.R
import com.plcoding.snoozeloo.alarm_selection.presentation.RingtonesManager
import com.plcoding.snoozeloo.core.domain.LockScreenAlarmActivity
import com.plcoding.snoozeloo.core.domain.db.AlarmsDatabase
import com.plcoding.snoozeloo.manager.domain.RescheduleAlarmUseCase
import com.plcoding.snoozeloo.scheduler.AlarmReceiver
import com.plcoding.snoozeloo.scheduler.AlarmReceiver.Companion.ALARM_ID
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
    private val recheduleAlarmUseCase: RescheduleAlarmUseCase by inject()

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val alarmId = intent?.getIntExtra(ALARM_ID, -1)
        if (alarmId == -1) {
            throw Error("Alarm id is equal to -1, that's no good")
        } else {
            alarmId?.let {
                when (intent?.action) {
                    Actions.START_FOREGROUND_SERVICE.toString() -> {
                        println("AlarmService action ${intent.action}")
                        startFullScreen(alarmId)
                    }

                    Actions.STOP_FOREGROUND_SERVICE_DISMISS.toString() -> {
                        println("AlarmService action ${intent.action}")
                        stopAlarm(alarmId)
                    }

                    Actions.STOP_FOREGROUND_SERVICE_SNOOZE.toString() -> {
                        println("AlarmService action ${intent.action}")
                        stopAlarm(alarmId = alarmId, wasSnoozed = true)
                    }
                }

                serviceScope.launch {
                    delay(TimeUnit.MINUTES.toMillis(1))
                    dbScope.launch {
                        try {
                            val alarmById = alarmsDatabase.alarmsDao().getAlarmById(alarmId)
                            val formattedTime = String.format(
                                Locale.current.platformLocale, "%02d:%02d",
                                alarmById.hours, alarmById.minutes
                            )
                            val notificationText =
                                "Alarm at: $formattedTime with id $alarmId was missed!"

                            val missedAlarmNotification = NotificationCompat.Builder(
                                this@AlarmService,
                                "ALARM_SERVICE_CHANNEL_ID"
                            )
                                .setContentTitle("Alarm missed")
                                .setContentText(notificationText)
                                .setSmallIcon(R.drawable.ic_launcher_foreground)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setAutoCancel(true) // Set if notification should be canceled when user click on it
                                .build()

                            val missedAlarmNotificationId = MISSED_REQUEST_CODE + alarmId
                            val notificationManager =
                                NotificationManagerCompat.from(this@AlarmService)

                            if (ActivityCompat.checkSelfPermission(
                                    this@AlarmService,
                                    Manifest.permission.POST_NOTIFICATIONS
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                notificationManager.notify(
                                    missedAlarmNotificationId,
                                    missedAlarmNotification
                                )
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                            return@launch
                        } finally {
                            stopAlarm(alarmId)
                        }
                    }
                }
            }
        }

        return START_STICKY
    }

    private fun startFullScreen(alarmId: Int) {
        // Play ringtone part remains the same
        dbScope.launch {
            try {
                val alarmById = alarmsDatabase.alarmsDao().getAlarmById(alarmId)
                val ringtoneUri = Uri.parse(alarmById.alarmRingtoneId)
                ringtonesManager.playRingtone(ringtoneUri)
                if (alarmById.shouldVibrate) ringtonesManager.startVibrating()
            } catch (e: Exception) {
                stopAlarm(alarmId)
                return@launch
            }
        }

        val dismissAlarmPendingIntent = PendingIntent.getBroadcast(
            this,
            DISMISS_REQUEST_CODE + alarmId, // Use alarmId as a salt for unique pending intents
            Intent(this, AlarmReceiver::class.java).apply {
                putExtra(AlarmReceiver.ALARM_ID, alarmId)
                putExtra(AlarmReceiver.ALARM_FLAG, AlarmReceiver.AlarmDismissType.DISMISS.name)
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val snoozeAlarmPendingIntent = PendingIntent.getBroadcast(
            this,
            SNOOZE_REQUEST_CODE + alarmId, // Use alarmId as a salt for unique pending intents
            Intent(this, AlarmReceiver::class.java).apply {
                putExtra(AlarmReceiver.ALARM_ID, alarmId)
                putExtra(AlarmReceiver.ALARM_FLAG, AlarmReceiver.AlarmDismissType.SNOOZE.name)
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Create intents with proper flags
        val fullScreenIntent = Intent(this, LockScreenAlarmActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(ALARM_ID, alarmId)
        }

        val fullScreenPendingIntent = PendingIntent.getActivity(
            this,
            FULL_SCREEN_ACTIVITY_REQUEST_CODE + alarmId,  // Use alarmId as a salt for unique pending intents
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
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .setOngoing(true)  // Make it persistent
            .setAutoCancel(false)  // Prevent auto-cancellation
            .addAction(R.drawable.icon_alarm, "Dismiss", dismissAlarmPendingIntent)
            .addAction(R.drawable.icon_alarm, "Snooze", snoozeAlarmPendingIntent)
            .build()

        // Start the full screen activity explicitly
        startActivity(fullScreenIntent)

        // Start foreground with notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            startForeground(
                FULL_SCREEN_ACTIVITY_REQUEST_CODE + alarmId,  // Use alarmId as notification id salt
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_SYSTEM_EXEMPTED
            )
        } else {
            startForeground(alarmId, notification)
        }
    }

    private fun stopAlarm(alarmId: Int, wasSnoozed: Boolean = false) {
        ringtonesManager.stopRingtone()
        ringtonesManager.stopVibrating()
        stopForeground(STOP_FOREGROUND_REMOVE)
        rescheduleAlarm(alarmId, wasSnoozed)
        stopAlarmActivityIfItIsOnForeground()
        stopSelf()
    }

    private fun rescheduleAlarm(alarmId: Int, wasSnoozed: Boolean) {
        dbScope.launch {
            try {
                alarmsDatabase.alarmsDao().getAlarmById(alarmId).run {
                    recheduleAlarmUseCase(this, wasSnoozed)
                }
            } catch (e: Exception) {
                stopAlarm(alarmId)
                return@launch
            }
        }
    }

    private fun stopAlarmActivityIfItIsOnForeground() {
        val closeIntent = Intent("ACTION_CLOSE_APP")
        LocalBroadcastManager.getInstance(this).sendBroadcast(closeIntent)
    }

    override fun onDestroy() {
        serviceScope.cancel()
        super.onDestroy()
    }

    enum class Actions {
        START_FOREGROUND_SERVICE,
        STOP_FOREGROUND_SERVICE_DISMISS,
        STOP_FOREGROUND_SERVICE_SNOOZE,
    }

    companion object {
        const val FULL_SCREEN_ACTIVITY_REQUEST_CODE = 10000
        const val DISMISS_REQUEST_CODE = 20000
        const val SNOOZE_REQUEST_CODE = 30000
        const val MISSED_REQUEST_CODE = 40000
    }
}