package com.plcoding.snoozeloo.core.domain

import com.plcoding.snoozeloo.core.domain.value.TimeValue
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.TemporalUnit
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

fun getTimeLeftToAlarm(currentTime: TimeValue, alarmTime: TimeValue): Pair<Long, Long> {
    return if (currentTime.value <= alarmTime.value) {
        getTimeDifferenceInHoursAndMinutes(currentTime.value, alarmTime.value)
    } else {
        getTimeDifferenceInHoursAndMinutes(currentTime.value, alarmTime.value + ONE_DAY_MILLIS)
    }
}

fun getTimeLeftToAlarm(currentTime: TimeValue, hours: Int, minutes: Int): Pair<Long, Long> {
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

fun formatNumberToTwoDigits(number: Int): String {
    return number.toString().padStart(2, '0')
}

fun findNextScheduleTimeEpochSeconds(scheduleHour: Int, scheduleMinute: Int, days: List<Boolean>): Long {
    // Return -1 if all days are false
    if (days.none { it }) {
        return -1L
    }

    val userTimeZone = ZoneId.systemDefault() // Get user's current time zone
    val now = ZonedDateTime.now(userTimeZone) // Get current time in user's time zone

    // Get current day index (0-based, Monday = 0)
    val currentDayIndex = now.dayOfWeek.value - 1

    val targetTime = LocalTime.of(scheduleHour, scheduleMinute)
    val currentTime = now.toLocalTime()

    if (days[currentDayIndex] && targetTime.isAfter(currentTime)) {
        return now.with(targetTime).toEpochSecond()
    }

    var daysToAdd = 1
    var nextDayIndex = (currentDayIndex + 1) % 7  // This % 7 is needed for wrapping

    while (daysToAdd <= 7) {
        if (days[nextDayIndex]) {
            return now.plusDays(daysToAdd.toLong())
                .with(targetTime)
                .toEpochSecond()
        }
        daysToAdd++
        nextDayIndex = (nextDayIndex + 1) % 7  // This % 7 is needed for wrapping
    }

    return
}

//    val scheduleTimeToday = now.with(LocalTime.of(scheduleHour, scheduleMinute))
//
//    val nextScheduleTime = if (scheduleTimeToday.isAfter(now)) {
//        scheduleTimeToday // Schedule time is in the future today
//    } else {
//        scheduleTimeToday.plusDays(1) // Schedule time has passed, use tomorrow
//    }
//
//    return nextScheduleTime.toEpochSecond() // Convert to Epoch seconds


fun findNextScheduleTimeEpochSeconds(amountToAdd: Long, unit: TemporalUnit): Long {
    val userTimeZone = ZoneId.systemDefault() // Get user's current time zone
    val now = ZonedDateTime.now(userTimeZone) // Get current time in user's time zone
    val nextScheduleTime = now.plus(amountToAdd, unit)

    return nextScheduleTime.toEpochSecond() // Convert to Epoch seconds
}
