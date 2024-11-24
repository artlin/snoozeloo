package com.plcoding.snoozeloo.manager.domain

import kotlinx.serialization.Serializable

@Serializable
data class AlarmEntity(
    val uid: String,
    val alarmTime: TimeValue,
    val alarmName: String,
    val isEnabled: Boolean,
    val ringtoneId: String,
    val isVibrateEnabled: Boolean,
    val volume: Float,
)
