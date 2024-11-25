package com.plcoding.snoozeloo.manager.domain

interface AlarmScheduler {
    fun scheduleAlarm(item: AlarmItem)
    fun cancelAlarm(item: AlarmItem)
}