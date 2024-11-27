package com.plcoding.snoozeloo.navigation.route

import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity
import com.plcoding.snoozeloo.core.domain.value.RingtoneId
import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoute {
    // Main Flow Routes
    @Serializable
    data object Alarms : NavigationRoute()

    @Serializable
    data class EditAlarm(val alarmEntity: AlarmEntity?) : NavigationRoute()

    @Serializable
    data class SelectRingtone(val currentRingtone: RingtoneId) : NavigationRoute()
}
