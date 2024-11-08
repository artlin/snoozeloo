package com.plcoding.snoozeloo.manager.ui.list

interface AlarmListEvent {
    data object AddAlarmClicked : AlarmListEvent
}

typealias OnAlarmList = (AlarmListEvent) -> Unit