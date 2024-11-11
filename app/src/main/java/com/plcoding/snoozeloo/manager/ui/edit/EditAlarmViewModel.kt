package com.plcoding.snoozeloo.manager.ui.edit

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.plcoding.snoozeloo.core.domain.entity.AlarmMetadata
import com.plcoding.snoozeloo.core.ui.ViewModelAccess
import com.plcoding.snoozeloo.navigation.NavigationController
import com.plcoding.snoozeloo.navigation.route.NavigationRoute
import kotlinx.coroutines.launch

class EditAlarmViewModel(
    private val alarmMetadata: AlarmMetadata,
    private val navigationController: NavigationController
) : ViewModel(),
    ViewModelAccess<EditAlarmState, EditAlarmEvent> {

    override var state: MutableState<EditAlarmState> = mutableStateOf(EditAlarmState())

    init {
        alarmMetadata
        // decide to fill with empty alarm values or existing values
        state.value = state.value.toNewAlarm()
    }

    override fun onEvent(event: EditAlarmEvent) {
        when (event) {
            is EditAlarmEvent.DigitEnteredFromKeyboard -> state.value =
                state.value.setNewDigit(event.digit)

            EditAlarmEvent.HoursComponentClicked -> state.value = state.value.startHoursEditMode()
            EditAlarmEvent.MinutesComponentClicked -> state.value =
                state.value.startMinutesEditMode()
        }
    }

}
