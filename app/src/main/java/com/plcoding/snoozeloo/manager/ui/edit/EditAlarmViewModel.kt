package com.plcoding.snoozeloo.manager.ui.edit

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.snoozeloo.alarm_selection.domain.GetSystemRingtonesUseCase
import com.plcoding.snoozeloo.alarm_selection.ui.SELECTED_RINGTONE_KEY
import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity
import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity.Companion.newAlarmEntity
import com.plcoding.snoozeloo.core.domain.entity.RingtoneEntity
import com.plcoding.snoozeloo.core.domain.value.RingtoneId
import com.plcoding.snoozeloo.core.domain.value.TimeValue
import com.plcoding.snoozeloo.core.ui.ViewModelAccess
import com.plcoding.snoozeloo.manager.domain.UpdateAlarmUseCase
import com.plcoding.snoozeloo.navigation.NavigationController
import com.plcoding.snoozeloo.navigation.route.NavigationRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class EditAlarmViewModel(
    private val ringtonesUseCase: GetSystemRingtonesUseCase,
    private val updateAlarmUseCase: UpdateAlarmUseCase,
    private val navigationController: NavigationController,
    private val savedStateHandle: SavedStateHandle,
    alarmEntityArgument: AlarmEntity? = null
) : ViewModel(),
    ViewModelAccess<UIStateEditAlarm, EditAlarmEvent> {

    override var state: MutableState<UIStateEditAlarm> = mutableStateOf(
        UIStateEditAlarm(
            ringtoneEntity = RingtoneEntity.asDefault()
        )
    )

    var newState: UIStateEditAlarm = UIStateEditAlarm(RingtoneEntity.asDefault())
        set(value) {
            state.value = value
        }

    private var alarmEntity: AlarmEntity

    init {
        if (alarmEntityArgument == null) {
            alarmEntity = newAlarmEntity()
            newState = state.value.toNewAlarm()
        } else {
            alarmEntity = alarmEntityArgument.copy()
            viewModelScope.launch {
                newState = state.value.fromEntity(alarmEntityArgument, ringtonesUseCase())
            }
        }
        viewModelScope.launch {
            savedStateHandle.getStateFlow<String?>(SELECTED_RINGTONE_KEY, null)
                .collect { ringtoneString ->
                    ringtoneString?.let { uriPath ->
                        // Update your state
                        val selectedRingtone =
                            ringtonesUseCase().firstOrNull { it.uri.toString() == uriPath }
                                ?: RingtoneEntity.asDefault()
                        newState = state.value.copy(
                            ringtoneEntity = selectedRingtone
                        )
                        // Clear the value
                        savedStateHandle[SELECTED_RINGTONE_KEY] = null
                    }
                    validateUi()
                }
        }
    }

    override fun onEvent(event: EditAlarmEvent) {
        when (event) {
            is EditAlarmEvent.DigitEnteredFromKeyboard -> {
                newState = state.value.setNewDigit(event.digit)
                if (state.value.isCompleted()) {
                    newState = state.value.toCorrectState().toAcceptedState()
                }
            }

            EditAlarmEvent.OnAlarmNameDismiss -> {
                val alarmNameSubState = state.value.alarmNameSubState.dismissDialogResetValues()
                newState = state.value.copy(alarmNameSubState = alarmNameSubState)
            }

            EditAlarmEvent.ChangeAlarmNameClicked -> {
                val alarmName = state.value.alarmNameSubState.name
                val alarmNameSubState = state.value.alarmNameSubState.openDialogWithName(alarmName)
                newState = state.value.copy(alarmNameSubState = alarmNameSubState)
            }

            is EditAlarmEvent.OnAlarmNameChanged -> {
                val newName = state.value.alarmNameSubState.updateName(event.name)
                newState = state.value.copy(alarmNameSubState = newName)
            }

            EditAlarmEvent.SaveAlarmNameClicked -> {
                val alarmNameSubState = state.value.alarmNameSubState
                    .saveNameIfNotEmpty()
                    .dismissDialogResetValues()
                newState = state.value.copy(alarmNameSubState = alarmNameSubState)

            }

            EditAlarmEvent.HoursComponentClicked -> newState = state.value.startHoursEdit()
            EditAlarmEvent.MinutesComponentClicked -> newState =
                state.value.startMinutesEdit()

            EditAlarmEvent.KeyboardIsHidden -> {
                if (state.value.isCompleted().not()) {
                    newState = state.value.toCorrectState().toAcceptedState()
                }
            }

            EditAlarmEvent.CancelClicked -> navigationController.navigateBack()
            EditAlarmEvent.SaveClicked -> {
                updateAlarmEntity()
                saveAlarm()
                navigationController.navigateBack()
            }

            EditAlarmEvent.SelectRingtoneClicked -> {
                navigationController.navigateTo(
                    NavigationRoute.SelectRingtone(
                        RingtoneId(
                            Uri.parse(
                                ""
                            )
                        )
                    )
                )
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
            alarmEntity = alarmEntity.copy(
                ringtoneId = state.value.ringtoneEntity.uri.toString(),
                alarmName = state.value.alarmNameSubState.name
            )
            updateAlarmUseCase(alarmEntity)
        }
    }

    private fun validateUi() {
        val currentTime = System.currentTimeMillis()

        val alarmHour = state.value.clockDigitStates.getHours()
        val alarmMinute = state.value.clockDigitStates.getMinutes()
        newState = state.value.validateButtons().validateDescription(
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
