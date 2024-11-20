package com.plcoding.snoozeloo.manager.ui.edit

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.snoozeloo.core.domain.entity.AlarmMetadata
import com.plcoding.snoozeloo.core.ui.ViewModelAccess
import com.plcoding.snoozeloo.manager.domain.EditAlarmState
import com.plcoding.snoozeloo.manager.domain.TimeValue
import com.plcoding.snoozeloo.navigation.NavigationController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.Calendar

class EditAlarmViewModel(
//    private val updateAlarmUseCase: UpdateAlarmUseCase,
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
            EditAlarmEvent.SaveClicked -> {
//                saveAlarm()
            }
        }
        validateUi()
    }

//    private fun saveAlarm() {
//        viewModelScope.launch(Dispatchers.IO) {
//            updateAlarmUseCase.updateAlarm(state.value.getAlarmData())
//        }
//    }

    private fun validateUi() {
        val currentTime = System.currentTimeMillis()

        val alarmHour = state.value.clockDigitStates.getHours()
        val alarmMinute = state.value.clockDigitStates.getMinutes()
        state.value = state.value.validateButtons().validateDescription(
            currentTime = TimeValue(currentTime),
            alarmTime = TimeValue(setTimeToSpecificHourAndMinute(currentTime,alarmHour,alarmMinute))
        )
    }

    fun setTimeToSpecificHourAndMinute(
        originalTimeInMillis: Long,
        targetHour: Int,
        targetMinute: Int
    ): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = originalTimeInMillis
            set(Calendar.HOUR_OF_DAY, targetHour)
            set(Calendar.MINUTE, targetMinute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }


}
