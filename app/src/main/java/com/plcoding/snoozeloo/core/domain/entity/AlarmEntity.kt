package com.plcoding.snoozeloo.core.domain.entity

import com.plcoding.snoozeloo.core.domain.value.TimeValue
import kotlinx.serialization.Serializable

@Serializable
data class AlarmEntity(
    val uid: Int,
    val alarmTime: TimeValue,
    val minutes: Int,
    val hours: Int,
    val alarmName: String,
    val isEnabled: Boolean,
    val ringtoneId: String,
    val isVibrateEnabled: Boolean,
    val volume: Float,
)

fun newAlarmEntity(): AlarmEntity = AlarmEntity(
    uid = 0,
    alarmTime = TimeValue(System.currentTimeMillis()),
    minutes = 0,
    hours = 0,
    alarmName = "",
    isEnabled = true,
    ringtoneId = "",
    isVibrateEnabled = false,
    volume = 0.5f
)
