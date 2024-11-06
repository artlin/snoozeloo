package com.plcoding.snoozeloo.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.plcoding.snoozeloo.manager.ui.AlarmListScreen
import com.plcoding.snoozeloo.manager.ui.EditAlarmScreen
import com.plcoding.snoozeloo.navigation.route.NavigationRoute


fun NavGraphBuilder.mainGraph() {
    navigation(
        startDestination = NavigationRoute.Alarms.route.value,
        route = Graph.Main.route
    ) {
        // ekran lista alarmow
        composable(NavigationRoute.Alarms.route.value) { AlarmListScreen() }
        // ekran edycja alarmow
        composable(NavigationRoute.EditAlarm.route.value) { EditAlarmScreen() }
    }
}