package com.plcoding.snoozeloo.manager.ui.edit

import com.plcoding.snoozeloo.core.domain.getAlarmInTime
import com.plcoding.snoozeloo.core.domain.value.TimeValue

data class ClockAlarmDescriptionState(
    val description: String = "",
    val isDescriptionEnabled: Boolean = false,
) {
    fun validateDescription(
        currentTime: TimeValue,
        alarmTime: TimeValue
    ): ClockAlarmDescriptionState {
        val difference = getAlarmInTime(currentTime, alarmTime)
        return copy(
            description = "Alarm in ${difference.first} H ${difference.second}min",
            isDescriptionEnabled = currentTime.value != alarmTime.value
        )
    }
}

