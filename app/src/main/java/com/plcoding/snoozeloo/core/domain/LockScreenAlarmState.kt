package com.plcoding.snoozeloo.core.domain

import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity

data class LockScreenAlarmState(
    val alarm: AlarmEntity? = null,
    val shouldClose: Boolean = false
)
