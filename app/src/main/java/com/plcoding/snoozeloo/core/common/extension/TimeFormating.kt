package com.plcoding.snoozeloo.core.common.extension

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
