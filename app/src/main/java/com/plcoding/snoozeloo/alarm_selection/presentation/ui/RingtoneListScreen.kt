package com.plcoding.snoozeloo.alarm_selection.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.plcoding.snoozeloo.alarm_selection.presentation.OnRingtoneEvent
import com.plcoding.snoozeloo.alarm_selection.presentation.RingtoneEvent
import com.plcoding.snoozeloo.core.domain.entity.RingtoneEntity
import com.plcoding.snoozeloo.core.domain.entity.SpecialRingtoneType
import com.plcoding.snoozeloo.core.domain.value.RingtoneId
import com.plcoding.snoozeloo.core.ui.headerbuttons.ButtonsState
import com.plcoding.snoozeloo.core.ui.headerbuttons.HeaderButtonType
import com.plcoding.snoozeloo.core.ui.headerbuttons.HeaderButtons
import com.plcoding.snoozeloo.core.ui.headerbuttons.SingleButtonState

@Composable
fun RingtoneListScreen(
    ringtones: List<RingtoneEntity>,
    selectedRingtone: RingtoneEntity,
    onRingtoneEvent: OnRingtoneEvent
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
            HeaderButtons(
                ButtonsState(
                    leftButton = SingleButtonState(buttonType = HeaderButtonType.BACK_ARROW),
                    rightButton = SingleButtonState()
                ),
                onEvent = { onRingtoneEvent(RingtoneEvent.BackButtonClicked) })
        }
        items(ringtones.size, key = ({ index -> ringtones[index].uri })) { index ->
            val ringtone = ringtones[index]
            RingtoneItemComponent(
                ringtone,
                isSelected = ringtone.uri.toString() == selectedRingtone.uri.toString(),
                onUriClick = {
                    onRingtoneEvent(RingtoneEvent.RingtoneSelected(RingtoneId(it)))
                })
        }
    }
}

fun RingtoneEntity.getLabel(): String {
    return when (specialType) {
        SpecialRingtoneType.MUTE -> "Silent"
        SpecialRingtoneType.DEFAULT -> "Default (${title.value})"
        SpecialRingtoneType.NONE -> title.value
    }
}

fun RingtoneEntity.getLabelShort(): String {
    return when (specialType) {
        SpecialRingtoneType.MUTE -> "Silent"
        SpecialRingtoneType.DEFAULT -> "Default"
        SpecialRingtoneType.NONE -> title.value
    }
}