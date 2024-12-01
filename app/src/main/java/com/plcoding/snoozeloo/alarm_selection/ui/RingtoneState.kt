package com.plcoding.snoozeloo.alarm_selection.ui

import com.plcoding.snoozeloo.core.domain.entity.RingtoneEntity

data class RingtoneState(
    val ringtones: List<RingtoneEntity> = emptyList(),
    val selectedRingtone: RingtoneEntity
)
