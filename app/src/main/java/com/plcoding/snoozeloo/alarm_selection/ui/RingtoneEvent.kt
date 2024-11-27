package com.plcoding.snoozeloo.alarm_selection.ui

import android.net.Uri
import com.plcoding.snoozeloo.core.domain.value.RingtoneId

sealed interface RingtoneEvent {
    data class RingtoneSelected(val ringtoneId: RingtoneId) : RingtoneEvent
}

typealias OnRingtoneEvent = (RingtoneEvent) -> Unit
typealias OnUriClick = (Uri) -> Unit