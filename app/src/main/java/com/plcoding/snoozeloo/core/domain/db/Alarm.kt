package com.plcoding.snoozeloo.core.domain.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.concurrent.TimeUnit

@Entity
data class Alarm(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val period: String, // Period
    val name: String,
    val minutes: Int,
    val hours: Int,
    val isActive: Boolean,
    val alarmRingtoneId: String,
    val shouldVibrate: Boolean,
    val volume: Float, // 0.0-1.0
    val defaultRingingTime: Long = TimeUnit.MINUTES.toMillis(5),
    val isEnabledAtWeekDay: String
){
    companion object {
        fun getDefault(): Alarm {
            return Alarm(
                id = 0,
                period = "AM", // Default period
                name = "Alarm", // Default name
                minutes = 0, // Default minutes
                hours = 7, // Default hours
                isActive = true, // Default active state
                alarmRingtoneId = "default_ringtone_id", // Default ringtone ID
                shouldVibrate = true, // Default vibration setting
                volume = 0.5f, // Default volume (50%)
                // defaultRingingTime uses the default value defined in the primary constructor
                isEnabledAtWeekDay = ""
            )
        }
    }
}