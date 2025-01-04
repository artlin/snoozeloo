package com.plcoding.snoozeloo.scheduler

import com.plcoding.snoozeloo.core.domain.db.Alarm

interface AlarmScheduler {
    fun scheduleAlarm(alarm: Alarm)
    fun cancelAlarm(alarm: Alarm)
    fun cancelAllAlarms()
}