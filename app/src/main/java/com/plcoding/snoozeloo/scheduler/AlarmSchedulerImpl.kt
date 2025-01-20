package com.plcoding.snoozeloo.scheduler

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.plcoding.snoozeloo.core.domain.db.Alarm
import com.plcoding.snoozeloo.core.domain.db.converters.Converters
import com.plcoding.snoozeloo.core.domain.findNextScheduleTimeEpochMillis
import com.plcoding.snoozeloo.scheduler.AlarmReceiver.Companion.ALARM_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.temporal.ChronoUnit

class AlarmSchedulerImpl(
    private val context: Context,
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    @SuppressLint("MissingPermission")
    override suspend fun scheduleAlarm(alarm: Alarm, wasSnoozed: Boolean) {
        withContext(Dispatchers.IO) {
            val intent = Intent(context, AlarmReceiver::class.java).apply {
                putExtra(ALARM_ID, alarm.id)
            }

            println("Alarm scheduled at ${alarm.hours} hours and ${alarm.minutes} minutes")

            val nextAlarmOccurrence = if (wasSnoozed) {
                findNextScheduleTimeEpochMillis(5L, ChronoUnit.MINUTES)
            } else {
                findNextScheduleTimeEpochMillis(
                    alarm.hours, alarm.minutes,
                    Converters().toBooleanList(alarm.isEnabledAtWeekDay)
                )
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                alarm.id,
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
        }
    }

    override fun cancelAlarm(alarm: Alarm) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarm.id,
                Intent(context, AlarmReceiver::class.java).apply {
                    putExtra(ALARM_ID, alarm.id)
                },
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

}