package com.plcoding.snoozeloo.manager.ui.list

interface AlarmListEvent {
    data object AddAlarmClicked : AlarmListEvent
    data class ToggleAlarmClicked(val alarmId: String) : AlarmListEvent
    data class AlarmCardClicked(val alarmId: String) : AlarmListEvent
}

typealias OnAlarmList = (AlarmListEvent) -> Unit