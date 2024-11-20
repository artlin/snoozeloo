package com.plcoding.snoozeloo.core.domain.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alarm(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val startTime: Long,
    val period: String, // Period
    val name: String,
    val isActive: Boolean,
    val alarmRingtoneId: String,
    val shouldVibrate: Boolean,
    val volume: Float, // 0.0-1.0
    val defaultRingingTime: Long = 5 * 60 * 1000L // Duration
    )