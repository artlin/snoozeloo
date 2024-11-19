package com.plcoding.snoozeloo.manager.ui.edit

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.plcoding.snoozeloo.core.domain.entity.AlarmMetadata
import com.plcoding.snoozeloo.core.ui.ViewModelAccess
import com.plcoding.snoozeloo.navigation.NavigationController

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
            is EditAlarmEvent.DigitEnteredFromKeyboard -> {
                state.value = state.value.setNewDigit(event.digit)
                if (state.value.isCompleted()) {
                    state.value = state.value.toCorrectState().toAcceptedState()
                }
            }

            EditAlarmEvent.HoursComponentClicked -> state.value = state.value.startHoursEdit()
            EditAlarmEvent.MinutesComponentClicked -> state.value =
                state.value.startMinutesEdit()

            EditAlarmEvent.KeyboardIsHidden -> {
                if (state.value.isCompleted().not()) {
                    state.value = state.value.toCorrectState().toAcceptedState()
                }
            }
            EditAlarmEvent.CancelClicked -> navigationController.navigateBack()
        }
    }

}
