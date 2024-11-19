package com.plcoding.snoozeloo.manager.ui.edit

sealed interface EditAlarmEvent {

    data object KeyboardIsHidden : EditAlarmEvent
    data object HoursComponentClicked : EditAlarmEvent
    data object MinutesComponentClicked : EditAlarmEvent
    data class DigitEnteredFromKeyboard(val digit: String) : EditAlarmEvent
    data object CancelClicked : EditAlarmEvent

}

typealias OnEditAlarm = (EditAlarmEvent) -> Unit