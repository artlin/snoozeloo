package com.plcoding.snoozeloo.navigation.route

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoute {
    // Main Flow Routes
    @Serializable
    data object Alarms : NavigationRoute()

    @Serializable
    data object EditAlarm : NavigationRoute()
}