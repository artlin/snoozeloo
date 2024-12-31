package com.plcoding.snoozeloo.manager.ui.edit

import com.plcoding.snoozeloo.core.domain.getTimeLeftToAlarm
import com.plcoding.snoozeloo.core.domain.value.TimeValue

data class ClockAlarmDescriptionState(
    val description: String = "",
    val isDescriptionEnabled: Boolean = false,
) {
    fun validateDescription(
        currentTime: TimeValue,
        alarmTime: TimeValue,
        isDescriptionEnabled: Boolean,
    ): ClockAlarmDescriptionState {
        val difference = getTimeLeftToAlarm(currentTime, alarmTime)
        return copy(
            description = "Alarm in ${difference.first} H ${difference.second}min",
            isDescriptionEnabled = isDescriptionEnabled
        )
    }
}

