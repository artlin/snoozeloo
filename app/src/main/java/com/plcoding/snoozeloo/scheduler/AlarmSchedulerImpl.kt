package com.plcoding.snoozeloo.scheduler

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.plcoding.snoozeloo.core.domain.db.Alarm
import com.plcoding.snoozeloo.core.domain.findNextScheduleTimeEpochSeconds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlarmSchedulerImpl(
    private val context: Context,
): AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    @SuppressLint("MissingPermission")
    override suspend fun scheduleAlarm(alarm: Alarm) {
        withContext(Dispatchers.IO) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("ALARM_ID", alarm.id)
        }
//        alarmManager.setAlarmClock()

        println("Alarm scheduled at ${alarm.hours} hours and ${alarm.minutes} minutes")

        val nextAlarmOccurrence = findNextScheduleTimeEpochSeconds(alarm.hours, alarm.minutes) * 1000L

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarm.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmInfo = AlarmManager.AlarmClockInfo(
            nextAlarmOccurrence,
            pendingIntent
        )

        alarmManager.setAlarmClock(
            alarmInfo,
            pendingIntent
        )

//        alarmManager.setExactAndAllowWhileIdle(
//            AlarmManager.RTC_WAKEUP,
//            nextAlarmOccurence,
//            PendingIntent.getBroadcast(
//                context,
//                alarm.hashCode(),
//                intent,
//                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//            )
//        )
        }
    }

//    override fun cancelAlarm(item: AlarmItem) {
//        alarmManager.cancel(
//            PendingIntent.getBroadcast(
//                context,
//                item.hashCode(),
//                Intent(context, AlarmReceiver::class.java),
//                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//            )
//        )
//    }


    override fun cancelAlarm(alarm: Alarm) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarm.hashCode(),
                Intent(context, AlarmReceiver::class.java).apply {
                    putExtra("ALARM_ID", alarm.id)
                },
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }


    override fun cancelAllAlarms() {
        alarmManager.cancelAll()
    }
}