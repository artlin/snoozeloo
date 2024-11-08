package com.plcoding.snoozeloo.manager.ui.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.plcoding.snoozeloo.core.domain.entity.AlarmMetadata
import com.plcoding.snoozeloo.core.ui.ViewModelAccess
import com.plcoding.snoozeloo.navigation.NavigationController
import com.plcoding.snoozeloo.navigation.route.NavigationRoute

class AlarmListViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val navigationController: NavigationController
) : ViewModel(),
    ViewModelAccess<AlarmListState, AlarmListEvent> {

    override var state: MutableState<AlarmListState> = mutableStateOf(AlarmListState())

    override fun onEvent(event: AlarmListEvent) {
        when (event) {
            AlarmListEvent.AddAlarmClicked -> {
                navigationController.navigateTo(NavigationRoute.EditAlarm(AlarmMetadata(isNew = true)))
            }
        }
    }

}
