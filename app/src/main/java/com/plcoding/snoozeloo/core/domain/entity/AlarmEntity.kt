package com.plcoding.snoozeloo.core.domain.entity

import com.plcoding.snoozeloo.core.domain.value.TimeValue
import kotlinx.serialization.Serializable

@Serializable
data class AlarmEntity(
    val uid: String,
    val alarmTime: TimeValue,
    val minutes: Int,
    val hours: Int,
    val alarmName: String,
    val isEnabled: Boolean,
    val ringtoneId: String,
    val isVibrateEnabled: Boolean,
    val volume: Float,
)
