package com.plcoding.snoozeloo.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.plcoding.snoozeloo.alarm_selection.ui.RingtoneListScreen
import com.plcoding.snoozeloo.alarm_selection.ui.RingtoneViewModel
import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity
import com.plcoding.snoozeloo.core.domain.value.RingtoneId
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
    innerPadding: PaddingValues,
) {

    NavHost(
        modifier = Modifier.padding(innerPadding).padding(10.dp),
        navController = navController,
        startDestination = NavigationRoute.Alarms
    ) {
        composable<NavigationRoute.Alarms> {
            val viewModel: AlarmListViewModel = koinViewModel()
            AlarmListScreen(viewModel.uiState.value, onAlarmList = { viewModel.onEvent(it) })
        }
        composable<NavigationRoute.EditAlarm>(
            typeMap = mapOf(
                typeOf<AlarmEntity?>() to CustomNavType.AlarmMetadataNavType
            )
        ) { backStackEntry ->
            val navParams = backStackEntry.toRoute<NavigationRoute.EditAlarm>()
            val viewModel: EditAlarmViewModel = koinViewModel {
                parametersOf(navParams.alarmEntity, backStackEntry.savedStateHandle)
            }
            EditAlarmScreen(viewModel.uiState.value, onEditAlarm = { viewModel.onEvent(it) })
        }
        composable<NavigationRoute.SelectRingtone>(
            typeMap = mapOf(
                typeOf<RingtoneId>() to CustomNavType.RingtoneIdNavType
            )
        ) { backStackEntry ->
            val navParams = backStackEntry.toRoute<NavigationRoute.SelectRingtone>()
            val viewModel: RingtoneViewModel = koinViewModel {
                parametersOf(navParams.currentRingtone)
            }
            RingtoneListScreen(
                ringtones = viewModel.uiState.value.ringtones,
                selectedRingtone = viewModel.uiState.value.selectedRingtone,
                onRingtoneEvent = { viewModel.onEvent(it) })
        }
    }
}
