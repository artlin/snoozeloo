package com.plcoding.snoozeloo.manager.ui.edit

import com.plcoding.snoozeloo.alarm_recurring.presentation.AlarmRepetitionSubState
import com.plcoding.snoozeloo.alarm_vibrate.presentation.AlarmVibrateSubState
import com.plcoding.snoozeloo.alarm_volume.presentation.AlarmVolumeSubState
import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity
import com.plcoding.snoozeloo.core.domain.entity.RingtoneEntity
import com.plcoding.snoozeloo.core.domain.value.HeaderButtonLabel
import com.plcoding.snoozeloo.core.domain.value.TimeValue
import com.plcoding.snoozeloo.core.ui.headerbuttons.ButtonsState
import com.plcoding.snoozeloo.core.ui.headerbuttons.HeaderButtonType
import com.plcoding.snoozeloo.core.ui.headerbuttons.SingleButtonState

data class UIStateEditAlarm(
    val isNewDefaultAlarm: Boolean = true,
    val selectedRingtoneEntity: RingtoneEntity,
    val alarmNameSubState: AlarmNameSubState = AlarmNameSubState.asDefault(),
    val alarmRepetitionSubState: AlarmRepetitionSubState = AlarmRepetitionSubState.getDefault(),
    val alarmVolumeSubState: AlarmVolumeSubState = AlarmVolumeSubState.getDefault(),
    val alarmVibrateSubState: AlarmVibrateSubState = AlarmVibrateSubState.getDefault(),
    val clockDescription: ClockAlarmDescriptionState = ClockAlarmDescriptionState(),
    val headerButtonStates: ButtonsState = ButtonsState(
        leftButton = SingleButtonState(
            buttonType = HeaderButtonType.CLOSE,
            enabled = true
        ), rightButton = SingleButtonState(
            buttonType = HeaderButtonType.SAVE,
            label = HeaderButtonLabel("Save")
        )
    ),
    val clockDigitStates: ClockDigitStates = ClockDigitStates()
) {

    fun toNewAlarm(defaultRingtone: RingtoneEntity): UIStateEditAlarm {
        return copy(
            isNewDefaultAlarm = true,
            clockDigitStates = clockDigitStates.asNewAlarm(),
            selectedRingtoneEntity = defaultRingtone
        )
    }

    fun startHoursEdit(): UIStateEditAlarm {
        return copy(clockDigitStates = clockDigitStates.startHoursEdit())
    }

    fun startMinutesEdit(): UIStateEditAlarm {
        return copy(clockDigitStates = clockDigitStates.startMinutesEdit())
    }

    fun setNewDigit(digit: String): UIStateEditAlarm {
        return copy(clockDigitStates = clockDigitStates.setNewDigit(digit))
    }

    fun isCompleted(): Boolean =
        clockDigitStates.allStates.all { it.value.state == DigitFieldState.IS_SET }

    fun toCorrectState(): UIStateEditAlarm =
        copy(clockDigitStates = clockDigitStates.toCorrectedState())

    fun toAcceptedState(): UIStateEditAlarm =
        copy(clockDigitStates = clockDigitStates.toAcceptedState(), isNewDefaultAlarm = false)

    fun validateButtons(): UIStateEditAlarm {
        val isSaveAlarmAllowed = isCompleted()
        val saveButton = headerButtonStates.rightButton
        return copy(
            headerButtonStates = headerButtonStates.copy(
                rightButton = saveButton.copy(
                    enabled = isSaveAlarmAllowed
                )
            )
        )
    }

    fun validateDescription(currentTime: TimeValue, alarmTime: TimeValue): UIStateEditAlarm {
        return copy(
            clockDescription = clockDescription.validateDescription(
                currentTime,
                alarmTime,
                isDescriptionEnabled = isNewDefaultAlarm.not()
            )
        )
    }

    fun fromEntity(
        alarmEntity: AlarmEntity,
        selectedRingtone: RingtoneEntity
    ): UIStateEditAlarm {
        val (hour, minutes) = alarmEntity.clockTime
        return copy(
            clockDigitStates = clockDigitStates.setupClock(hour, minutes),
            alarmNameSubState = AlarmNameSubState.fromName(alarmName = alarmEntity.alarmName),
            selectedRingtoneEntity = selectedRingtone
        )
    }

    fun toggleRepetitionDay(atIndex: Int): UIStateEditAlarm {
        return copy(alarmRepetitionSubState = alarmRepetitionSubState.toggleDay(atIndex))
    }

    fun changeAlarmVolume(newVolume: Float): UIStateEditAlarm {
        return copy(alarmVolumeSubState = alarmVolumeSubState.changeVolume(newVolume))
    }

    fun toggleVibrate(): UIStateEditAlarm {
        return copy(alarmVibrateSubState = alarmVibrateSubState.toggleVibrate())
    }

}
