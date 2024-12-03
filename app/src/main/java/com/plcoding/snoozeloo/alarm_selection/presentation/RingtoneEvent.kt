package com.plcoding.snoozeloo.alarm_selection.presentation

import android.net.Uri
import com.plcoding.snoozeloo.core.domain.value.RingtoneId

sealed interface RingtoneEvent {
    data class RingtoneSelected(val ringtoneId: RingtoneId) : RingtoneEvent
    data object BackButtonClicked : RingtoneEvent
}

typealias OnRingtoneEvent = (RingtoneEvent) -> Unit
typealias OnUriClick = (Uri) -> Unit