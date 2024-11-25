package com.plcoding.snoozeloo.core.domain

import com.plcoding.snoozeloo.core.domain.value.TimeValue
import java.util.Calendar
import java.util.concurrent.TimeUnit
import kotlin.math.abs

val ONE_DAY_MILLIS = TimeUnit.DAYS.toMillis(1)

fun getAlarmTime(alarmTime: TimeValue): Pair<Int, Int> {
    val calendar = Calendar.getInstance().apply {
        timeInMillis = alarmTime.value
    }
    val hours = calendar.get(Calendar.HOUR_OF_DAY) // 24-hour format hour
    val minutes = calendar.get(Calendar.MINUTE)   // Minutes
    return hours to minutes
}

fun getAlarmInTime(currentTime: TimeValue, alarmTime: TimeValue): Pair<Long, Long> {
    return if (currentTime.value <= alarmTime.value) {
        getTimeDifferenceInHoursAndMinutes(currentTime.value, alarmTime.value)
    } else {
        getTimeDifferenceInHoursAndMinutes(currentTime.value, alarmTime.value + ONE_DAY_MILLIS)
    }
}

fun getAlarmInTime(currentTime: TimeValue, hours: Int, minutes: Int): Pair<Long, Long> {
    val calendar = Calendar.getInstance().apply {
        timeInMillis = currentTime.value
    }
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    val currentMinute = calendar.get(Calendar.MINUTE)

    val currentMinutes = currentHour * 60 + currentMinute
    var targetMinutes = hours * 60 + minutes

    // If target time is earlier today, add 24 hours
    if (targetMinutes <= currentMinutes) {
        targetMinutes += 24 * 60
    }

    val diffMinutes = targetMinutes - currentMinutes
    return Pair((diffMinutes / 60).toLong(), (diffMinutes % 60).toLong())
}

fun getTimeDifferenceInHoursAndMinutes(timestamp1: Long, timestamp2: Long): Pair<Long, Long> {
    // Calculate the difference in milliseconds
    val differenceInMillis = abs(timestamp2 - timestamp1)

    // Convert the difference to hours and minutes
    val hours = differenceInMillis / (1000 * 60 * 60)
    val minutes = (differenceInMillis % (1000 * 60 * 60)) / (1000 * 60)

    return hours to minutes
}

fun getTimeComponents(timestamp: Long): Pair<Int, Int> {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp

    val hours = calendar.get(Calendar.HOUR_OF_DAY)
    val minutes = calendar.get(Calendar.MINUTE)

    return Pair(hours, minutes)
}