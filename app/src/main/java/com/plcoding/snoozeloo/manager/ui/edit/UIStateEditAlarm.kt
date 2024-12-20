package com.plcoding.snoozeloo.manager.ui.edit

import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity
import com.plcoding.snoozeloo.core.domain.entity.RingtoneEntity
import com.plcoding.snoozeloo.core.domain.value.HeaderButtonLabel
import com.plcoding.snoozeloo.core.domain.value.TimeValue
import com.plcoding.snoozeloo.core.ui.headerbuttons.ButtonsState
import com.plcoding.snoozeloo.core.ui.headerbuttons.HeaderButtonType
import com.plcoding.snoozeloo.core.ui.headerbuttons.SingleButtonState

data class UIStateEditAlarm(
    val selectedRingtoneEntity: RingtoneEntity,
    val alarmNameSubState: AlarmNameSubState = AlarmNameSubState.asDefault(),
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
        copy(clockDigitStates = clockDigitStates.toAcceptedState())

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
        return copy(clockDescription = clockDescription.validateDescription(currentTime, alarmTime))
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

}
