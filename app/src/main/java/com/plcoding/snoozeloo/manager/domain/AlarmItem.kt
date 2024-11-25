package com.plcoding.snoozeloo.manager.domain

import java.time.LocalDateTime

data class AlarmItem(
    val time: LocalDateTime,
    val message: String
)
