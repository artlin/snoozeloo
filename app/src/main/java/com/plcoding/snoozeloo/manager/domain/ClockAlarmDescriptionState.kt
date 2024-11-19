package com.plcoding.snoozeloo.manager.domain

import com.plcoding.snoozeloo.manager.domain.Consts.ONE_DAY_MILLIS
import java.util.concurrent.TimeUnit

object Consts {
    val ONE_DAY_MILLIS = TimeUnit.DAYS.toMillis(1)
}

data class ClockAlarmDescriptionState(
    val description: String = "",
    val isDescriptionEnabled: Boolean = false,
) {
    fun validateDescription(
        currentTime: TimeValue,
        alarmTime: TimeValue
    ): ClockAlarmDescriptionState {
        val difference = if (currentTime.value <= alarmTime.value) {
            getTimeDifferenceInHoursAndMinutes(currentTime.value, alarmTime.value)
        } else {
            getTimeDifferenceInHoursAndMinutes(currentTime.value, alarmTime.value + ONE_DAY_MILLIS)
        }
        return copy(
            description = "Alarm in ${difference.first} H ${difference.second}min",
            isDescriptionEnabled = currentTime.value != alarmTime.value
        )
    }
}

fun getTimeDifferenceInHoursAndMinutes(timestamp1: Long, timestamp2: Long): Pair<Long, Long> {
    // Calculate the difference in milliseconds
    val differenceInMillis = Math.abs(timestamp2 - timestamp1)

    // Convert the difference to hours and minutes
    val hours = differenceInMillis / (1000 * 60 * 60)
    val minutes = (differenceInMillis % (1000 * 60 * 60)) / (1000 * 60)

    return hours to minutes
}