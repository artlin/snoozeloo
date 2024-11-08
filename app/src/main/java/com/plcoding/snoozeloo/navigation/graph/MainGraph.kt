package com.plcoding.snoozeloo.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.plcoding.snoozeloo.manager.ui.list.AlarmListScreen
import com.plcoding.snoozeloo.manager.ui.edit.EditAlarmScreen
import com.plcoding.snoozeloo.navigation.route.NavigationRoute


fun NavGraphBuilder.mainGraph() {
    navigation(
        startDestination = NavigationRoute.EditAlarm.route.value,
        route = Graph.Main.route
    ) {
        composable(NavigationRoute.Alarms.route.value) { AlarmListScreen() }
        composable(NavigationRoute.EditAlarm.route.value) { EditAlarmScreen() }
    }
}