package com.plcoding.snoozeloo.navigation.graph

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.plcoding.snoozeloo.manager.domain.AlarmEntity
import com.plcoding.snoozeloo.manager.ui.edit.EditAlarmScreen
import com.plcoding.snoozeloo.manager.ui.edit.EditAlarmViewModel
import com.plcoding.snoozeloo.manager.ui.list.AlarmListScreen
import com.plcoding.snoozeloo.manager.ui.list.AlarmListViewModel
import com.plcoding.snoozeloo.navigation.custom.CustomNavType
import com.plcoding.snoozeloo.navigation.route.NavigationRoute
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import kotlin.reflect.typeOf

@Composable
fun RootGraph(
    navController: NavHostController,
) {
    NavHost(
        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
        navController = navController,
        startDestination = NavigationRoute.Alarms
    ) {
        composable<NavigationRoute.Alarms> {
            val viewModel: AlarmListViewModel = koinViewModel()
            AlarmListScreen(viewModel.state.value, onAlarmList = { viewModel.onEvent(it) })
        }
        composable<NavigationRoute.EditAlarm>(
            typeMap = mapOf(
                typeOf<AlarmEntity?>() to CustomNavType.AlarmMetadataNavType
            )
        ) { backStackEntry ->
            val navParams = backStackEntry.toRoute<NavigationRoute.EditAlarm>()
            val viewModel: EditAlarmViewModel = koinViewModel()
            EditAlarmScreen(viewModel.state.value, onEditAlarm = { viewModel.onEvent(it) })
        }
    }
}