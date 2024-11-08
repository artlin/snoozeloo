package com.plcoding.snoozeloo.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.plcoding.snoozeloo.manager.ui.edit.EditAlarmScreen
import com.plcoding.snoozeloo.manager.ui.list.AlarmListScreen
import com.plcoding.snoozeloo.manager.ui.list.AlarmListViewModel
import com.plcoding.snoozeloo.navigation.route.NavigationRoute
import org.koin.compose.viewmodel.koinViewModel


fun NavGraphBuilder.mainGraph() {
    navigation(
        startDestination = NavigationRoute.Alarms.route.value,
        route = Graph.Main.route
    ) {
        composable(NavigationRoute.Alarms.route.value) {
            val viewModel: AlarmListViewModel = koinViewModel()
            AlarmListScreen(viewModel.state.value, onAlarmList = { viewModel.onEvent(it) })
        }
        composable(NavigationRoute.EditAlarm.route.value) { EditAlarmScreen() }
    }
}