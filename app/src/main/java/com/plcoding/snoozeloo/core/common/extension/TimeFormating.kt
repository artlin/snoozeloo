package com.plcoding.snoozeloo.core.common.extension

import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun Pair<Int, Int>.toAlarmTime(): String {
    return StringBuilder().apply {
        if (first < 10) append("0")
        append("$first")
        append(":")
        if (second < 10) append("0")
        append("$second")
    }.toString()
}

fun Pair<Long, Long>.toAlarmInTime(): String {
    return StringBuilder().apply {
        append("Alarm in")
        if (first > 0) append(" ${first}h")
        if (second < 10) append(" 0")
        else append(" ")
        append("$second")
        append("min")
    }.toString()
}


fun Pair<Int, Int>.toTime8HoursBeforeAlarm(): String {
    // Convert the alarm time (Pair<Int, Int>) to LocalTime
    val alarmTime = LocalTime.of(first, second)
    // Subtract 8 hours
    val timeToGoToBed = alarmTime.minusHours(8)
    // Format the result as a readable string
    val formatter = DateTimeFormatter.ofPattern("hh:mma") // 12-hour format with AM/PM
    return timeToGoToBed.format(formatter).lowercase()
}

fun Pair<Int, Int>.toBedTimeText(): String {
    val bedTime = this.toTime8HoursBeforeAlarm()
    return "Go to bed at $bedTime to get 8h of sleep"
}
