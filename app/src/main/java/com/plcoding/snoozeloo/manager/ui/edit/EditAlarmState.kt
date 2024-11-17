package com.plcoding.snoozeloo.manager.ui.edit

data class EditAlarmState(
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
        clockDigitStates.currentSelectionState == FocusedSelection.COMPLETED

    fun toCorrectState(): EditAlarmState =
        copy(clockDigitStates = clockDigitStates.toCorrectedState())

    fun toInactiveState(): EditAlarmState =
        copy(clockDigitStates = clockDigitStates.toInactiveState())
}
