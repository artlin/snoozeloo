package com.plcoding.snoozeloo.manager.domain

import com.plcoding.snoozeloo.core.domain.getAlarmInTime

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

