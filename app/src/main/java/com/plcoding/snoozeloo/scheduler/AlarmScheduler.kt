package com.plcoding.snoozeloo.scheduler

interface AlarmScheduler {
    fun scheduleAlarm(item: AlarmItem)
    fun cancelAlarm(item: AlarmItem)
}