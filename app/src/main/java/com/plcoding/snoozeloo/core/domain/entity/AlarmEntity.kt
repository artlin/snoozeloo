package com.plcoding.snoozeloo.core.domain.entity

import com.plcoding.snoozeloo.core.domain.value.AlarmName
import kotlinx.serialization.Serializable

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

    fun updateRingtone(ringtoneId: String): AlarmEntity {
        return copy(ringtoneId = ringtoneId)
    }

    fun updateAlarmName(alarmName: AlarmName): AlarmEntity {
        return copy(alarmName = alarmName)
    }

    fun updateRingtoneVolume(currentVolume: Float): AlarmEntity {
        return copy(volume = currentVolume)
    }

    fun updateRingtoneVibration(vibrateEnabled: Boolean): AlarmEntity {
        return copy(isVibrateEnabled = vibrateEnabled)
    }

    companion object {

        fun newAlarmEntity(): AlarmEntity = AlarmEntity(
            uid = 0,
            clockTime = ClockTime.asEmpty(),
            alarmName = AlarmName(""),
            isEnabled = true,
            ringtoneId = "",
            isVibrateEnabled = false,
            volume = 0.5f
        )
    }

}

