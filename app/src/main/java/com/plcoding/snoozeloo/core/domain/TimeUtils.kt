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

fun getTimeLeftToAlarm(currentTime: TimeValue, alarmTime: TimeValue): Pair<Long, Long> {
    return if (currentTime.value <= alarmTime.value) {
        getTimeDifferenceInHoursAndMinutes(currentTime.value, alarmTime.value)
    } else {
        getTimeDifferenceInHoursAndMinutes(currentTime.value, alarmTime.value + ONE_DAY_MILLIS)
    }
}

fun getTimeLeftToAlarm(
    currentTime: TimeValue,
    days: List<Boolean>,
    hours: Int,
    minutes: Int
): Pair<Long, Long> {
    val calendar = Calendar.getInstance().apply {
        timeInMillis = currentTime.value
    }

    // converted to 0-6 format
    val whichDayNow = calendar.get(Calendar.DAY_OF_WEEK) - 1
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    val currentMinute = calendar.get(Calendar.MINUTE)

    val daysUntilAlarm = findFirstTrue(days, whichDayNow)
    val currentMinutes = currentHour * 60 + currentMinute
    val targetMinutes = hours * 60 + minutes

    val totalMinutes = when {
        // If alarm is today but already passed, find next day's alarm
        daysUntilAlarm == 0 && targetMinutes <= currentMinutes -> {
            val nextDayAlarm = findFirstTrue(days, (whichDayNow + 1) % 7)
            (nextDayAlarm * 24 * 60) + (targetMinutes - currentMinutes) + (24 * 60)
        }
        // If alarm is today and hasn't passed yet
        daysUntilAlarm == 0 -> targetMinutes - currentMinutes
        // If alarm is on a future day
        else -> (daysUntilAlarm * 24 * 60) + (targetMinutes - currentMinutes)
    }

    return Pair(totalMinutes / 60L, totalMinutes % 60L)
}

fun findFirstTrue(days: List<Boolean>, startDay: Int): Int {
    if (days.isEmpty()) return 0

    val n = days.size
    var currentDay = startDay
    var daysUntilAlarm = 0

    // Check up to 7 days
    repeat(n) {
        if (days[currentDay]) {
            return daysUntilAlarm
        }
        daysUntilAlarm++
        currentDay = (currentDay + 1) % n
    }

    return 0 // No alarm days found
}

fun getTimeDifferenceInHoursAndMinutes(timestamp1: Long, timestamp2: Long): Pair<Long, Long> {
    // Calculate the difference in milliseconds
    val differenceInMillis = abs(timestamp2 - timestamp1)

    // Convert the difference to hours and minutes
    val hours = differenceInMillis / (1000 * 60 * 60)
    val minutes = (differenceInMillis % (1000 * 60 * 60)) / (1000 * 60)

    return hours to minutes
}

fun formatNumberToTwoDigits(number: Int): String {
    return number.toString().padStart(2, '0')
}

const val NEXT_SCHEDULE_TIME_NOT_FOUND = -1L

fun findNextScheduleTimeEpochMillis(
    scheduleHour: Int,
    scheduleMinute: Int,
    days: List<Boolean>
): Long {
    // Return -1 if all days are false
    if (days.none { it }) {
        return NEXT_SCHEDULE_TIME_NOT_FOUND
    }

    val userTimeZone = ZoneId.systemDefault() // Get user's current time zone
    val now = ZonedDateTime.now(userTimeZone) // Get current time in user's time zone

    // Get current day index (0-based, Monday = 0)
    val currentDayIndex = now.dayOfWeek.value - 1

    val targetTime = LocalTime.of(scheduleHour, scheduleMinute)
    val currentTime = now.toLocalTime()

    if (days[currentDayIndex] && targetTime.isAfter(currentTime)) {
        return now.with(targetTime).toEpochSecond() * 1000L
    }

    var daysToAdd = 1
    var nextDayIndex = (currentDayIndex + 1) % 7  // This % 7 is needed for wrapping

    while (daysToAdd <= 7) {
        if (days[nextDayIndex]) {
            return now.plusDays(daysToAdd.toLong())
                .with(targetTime)
                .toEpochSecond() * 1000L
        }
        daysToAdd++
        nextDayIndex = (nextDayIndex + 1) % 7  // This % 7 is needed for wrapping
    }

    return NEXT_SCHEDULE_TIME_NOT_FOUND
}

fun findNextScheduleTimeEpochMillis(amountToAdd: Long, unit: TemporalUnit): Long {
    val userTimeZone = ZoneId.systemDefault() // Get user's current time zone
    val now = ZonedDateTime.now(userTimeZone) // Get current time in user's time zone
    val nextScheduleTime = now.plus(amountToAdd, unit)

    return nextScheduleTime.toEpochSecond() * 1000L// Convert to Epoch seconds
}
