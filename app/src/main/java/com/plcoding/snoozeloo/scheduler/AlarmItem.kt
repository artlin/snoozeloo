package com.plcoding.snoozeloo.scheduler

import java.time.LocalDateTime

data class AlarmItem(
    val time: LocalDateTime,
    val message: String
)