package com.plcoding.snoozeloo.core.domain.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoute(
    val route: String
) {
    // Main Flow Routes
    @Serializable
    data object Alarms : NavigationRoute("alarms")

    @Serializable
    data object EditAlarm : NavigationRoute("edit_alarm")
}