package com.plcoding.snoozeloo.core.domain.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.concurrent.TimeUnit

@Entity
data class Alarm(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val startTime: Long,
    val period: String, // Period
    val name: String,
    val minutes: Int,
    val hours: Int,
    val isActive: Boolean,
    val alarmRingtoneId: String,
    val shouldVibrate: Boolean,
    val volume: Float, // 0.0-1.0
    val defaultRingingTime: Long = TimeUnit.MINUTES.toMillis(5)
)