package com.plcoding.snoozeloo.manager.ui.edit

import com.plcoding.snoozeloo.core.domain.value.AlarmName

sealed interface EditAlarmEvent {

    data object KeyboardIsHidden : EditAlarmEvent
    data object HoursComponentClicked : EditAlarmEvent
    data object MinutesComponentClicked : EditAlarmEvent
    data class DigitEnteredFromKeyboard(val digit: String) : EditAlarmEvent
    data class OnAlarmNameChanged(val name: AlarmName) : EditAlarmEvent
    data object CancelClicked : EditAlarmEvent
    data object SaveClicked : EditAlarmEvent
    data object SelectRingtoneClicked : EditAlarmEvent
    data object ChangeAlarmNameClicked : EditAlarmEvent
    data object OnAlarmNameDismiss : EditAlarmEvent
    data object SaveAlarmNameClicked : EditAlarmEvent
    data class OnRepetitionDayClicked(val index: Int) : EditAlarmEvent
    data class OnAlarmVolumeChanged(val volume: Float) : EditAlarmEvent
    data object OnAlarmVibrateClicked : EditAlarmEvent
    data object RemoveAlarmClicked : EditAlarmEvent
}

typealias OnEditAlarm = (EditAlarmEvent) -> Unit