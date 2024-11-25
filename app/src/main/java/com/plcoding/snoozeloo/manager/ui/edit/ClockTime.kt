package com.plcoding.snoozeloo.manager.ui.edit

import com.plcoding.snoozeloo.core.domain.value.TimeValue

data class ClockTime(
    val hours: TimeValue,
    val minutes: TimeValue
)
