package com.plcoding.snoozeloo.manager.ui.edit

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.snoozeloo.core.ui.ViewModelAccess
import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity
import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity.Companion.newAlarmEntity
import com.plcoding.snoozeloo.core.domain.value.TimeValue
import com.plcoding.snoozeloo.manager.domain.UpdateAlarmUseCase
import com.plcoding.snoozeloo.navigation.NavigationController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class EditAlarmViewModel(
    private val updateAlarmUseCase: UpdateAlarmUseCase,
    private val navigationController: NavigationController,
    private val alarmEntityArgument: AlarmEntity? = null
) : ViewModel(),
    ViewModelAccess<EditAlarmState, EditAlarmEvent> {

    override var state: MutableState<EditAlarmState> = mutableStateOf(EditAlarmState())

    private var alarmEntity: AlarmEntity

    init {
        if (alarmEntityArgument == null) {
            alarmEntity = newAlarmEntity()
            state.value = state.value.toNewAlarm()
        } else {
            alarmEntity = alarmEntityArgument.copy()
            state.value = state.value.fromEntity(alarmEntityArgument)
        }
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
                updateAlarmEntity()
                saveAlarm()
            }
        }
        validateUi()
    }

    private fun updateAlarmEntity() {
        alarmEntity = alarmEntity.updateClock(
            hours = state.value.clockDigitStates.getHours(),
            minutes = state.value.clockDigitStates.getMinutes(),
        )
    }

    private fun saveAlarm() {
        viewModelScope.launch(Dispatchers.IO) {
            updateAlarmUseCase(alarmEntity)
        }
    }

    private fun validateUi() {
        val currentTime = System.currentTimeMillis()

        val alarmHour = state.value.clockDigitStates.getHours()
        val alarmMinute = state.value.clockDigitStates.getMinutes()
        state.value = state.value.validateButtons().validateDescription(
            currentTime = TimeValue(currentTime),
            alarmTime = TimeValue(
                setTimeToSpecificHourAndMinute(
                    currentTime,
                    alarmHour,
                    alarmMinute
                )
            )
        )
    }

    private fun setTimeToSpecificHourAndMinute(
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
