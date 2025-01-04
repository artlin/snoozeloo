package com.plcoding.snoozeloo.manager.ui.list

import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity

interface AlarmListEvent {
    data object AddAlarmClicked : AlarmListEvent
//    data class ToggleAlarmClicked(val alarmId: Int, val isChecked: Boolean) : AlarmListEvent
    data class ToggleAlarmClicked(val alarmEntity: AlarmEntity, val isChecked: Boolean) : AlarmListEvent
    data class AlarmCardClicked(val alarmEntity: AlarmEntity) : AlarmListEvent
}

typealias OnAlarmList = (AlarmListEvent) -> Unit