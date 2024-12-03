package com.plcoding.snoozeloo.core.domain.entity

import com.plcoding.snoozeloo.core.domain.value.AlarmName
import com.plcoding.snoozeloo.core.domain.value.TimeValue
import kotlinx.serialization.Serializable
import kotlin.math.min

@Serializable
data class AlarmEntity(
    val uid: Int,
    val clockTime: ClockTime = ClockTime.asEmpty(),
    val alarmName: AlarmName,
    val isEnabled: Boolean,
    val ringtoneId: String,
    val isVibrateEnabled: Boolean,
    val volume: Float
) {
    val hours: Int
        get() {
            return clockTime.hours
        }

    val minutes: Int
        get() {
            return clockTime.minutes
        }

    fun updateClock(hours: Int, minutes: Int): AlarmEntity {
        return copy(clockTime = clockTime.copy(hours = hours, minutes = minutes))
    }


    companion object {

        fun newAlarmEntity(): AlarmEntity = AlarmEntity(
            uid = 0,
            clockTime = ClockTime.asEmpty(),
            alarmName = AlarmName("")   ,
            isEnabled = true,
            ringtoneId = "",
            isVibrateEnabled = false,
            volume = 0.5f
        )
    }

}

