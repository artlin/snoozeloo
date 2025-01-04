package com.plcoding.snoozeloo.core.domain

interface LockScreenAlarmEvent {
    data class AlarmSet(val alarmId: Int) : LockScreenAlarmEvent
    data object CloseClicked : LockScreenAlarmEvent
    data object SnoozeClicked : LockScreenAlarmEvent
}

typealias OnLockScreenAlarm = (LockScreenAlarmEvent) -> Unit