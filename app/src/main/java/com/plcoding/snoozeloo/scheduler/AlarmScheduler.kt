package com.plcoding.snoozeloo.scheduler

import com.plcoding.snoozeloo.core.domain.db.Alarm

interface AlarmScheduler {
    suspend fun scheduleAlarm(alarm: Alarm)
    fun cancelAlarm(alarm: Alarm)
    fun cancelAllAlarms()
}