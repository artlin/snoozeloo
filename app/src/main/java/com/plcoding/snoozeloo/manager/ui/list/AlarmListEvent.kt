package com.plcoding.snoozeloo.manager.ui.list

interface AlarmListEvent {
    data object AddAlarmClicked : AlarmListEvent
    data class ToggleAlarmClicked(val alarmId: Int) : AlarmListEvent
    data class AlarmCardClicked(val alarmId: Int) : AlarmListEvent
}

typealias OnAlarmList = (AlarmListEvent) -> Unit