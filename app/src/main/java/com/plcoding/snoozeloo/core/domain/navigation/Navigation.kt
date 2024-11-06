package com.plcoding.snoozeloo.core.domain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.plcoding.snoozeloo.manager.ui.AlarmListScreen
import com.plcoding.snoozeloo.manager.ui.EditAlarmScreen


@Composable
fun RootGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Graph.Main.route
) {
    NavHost(
        navController = navController,
        route = Graph.Root.route,
        startDestination = startDestination
    ) {
        // Main Graph
        mainGraph(navController)
    }
}


fun NavGraphBuilder.mainGraph(navController: NavHostController) {
    navigation(
        startDestination = NavigationRoute.Alarms.route,
        route = Graph.Main.route
    ) {
        // ekran lista alarmow
        composable(NavigationRoute.Alarms.route) { AlarmListScreen(navController) }
        // ekran edycja alarmow
        composable(NavigationRoute.EditAlarm.route) { EditAlarmScreen(navController) }
    }
}