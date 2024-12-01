package com.plcoding.snoozeloo.manager.ui.edit

import android.net.Uri
import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity
import com.plcoding.snoozeloo.core.domain.entity.RingtoneEntity
import com.plcoding.snoozeloo.core.domain.value.HeaderButtonLabel
import com.plcoding.snoozeloo.core.domain.value.TimeValue
import com.plcoding.snoozeloo.core.ui.headerbuttons.ButtonsState
import com.plcoding.snoozeloo.core.ui.headerbuttons.HeaderButtonType
import com.plcoding.snoozeloo.core.ui.headerbuttons.SingleButtonState

data class EditAlarmState(
    val ringtoneEntity: RingtoneEntity,
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
    fun toNewAlarm(): EditAlarmState {
        return copy(clockDigitStates = clockDigitStates.asNewAlarm())
    }

    fun startHoursEdit(): EditAlarmState {
        return copy(clockDigitStates = clockDigitStates.startHoursEdit())
    }

    fun startMinutesEdit(): EditAlarmState {
        return copy(clockDigitStates = clockDigitStates.startMinutesEdit())
    }

    fun setNewDigit(digit: String): EditAlarmState {
        return copy(clockDigitStates = clockDigitStates.setNewDigit(digit))
    }

    fun isCompleted(): Boolean =
        clockDigitStates.allStates.all { it.value.state == DigitFieldState.IS_SET }

    fun toCorrectState(): EditAlarmState =
        copy(clockDigitStates = clockDigitStates.toCorrectedState())

    fun toAcceptedState(): EditAlarmState =
        copy(clockDigitStates = clockDigitStates.toAcceptedState())

    fun validateButtons(): EditAlarmState {
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

    fun validateDescription(currentTime: TimeValue, alarmTime: TimeValue): EditAlarmState {
        return copy(clockDescription = clockDescription.validateDescription(currentTime, alarmTime))
    }

    fun fromEntity(
        alarmEntity: AlarmEntity,
        ringtones: MutableList<RingtoneEntity>
    ): EditAlarmState {
        val (hour, minutes) = alarmEntity.clockTime
        val alarmUri = Uri.parse(alarmEntity.ringtoneId)
        val ringtone: RingtoneEntity = ringtones.firstOrNull { it.uri == alarmUri } ?: RingtoneEntity.asDefault()
        return copy(
            clockDigitStates = clockDigitStates.setupClock(hour, minutes),
            ringtoneEntity = ringtone
        )
    }

}
