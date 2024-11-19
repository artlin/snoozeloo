package com.plcoding.snoozeloo.manager.ui.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.snoozeloo.core.domain.value.HeaderButtonLabel
import com.plcoding.snoozeloo.core.ui.headerbuttons.ButtonsState
import com.plcoding.snoozeloo.core.ui.headerbuttons.HeaderButtonType
import com.plcoding.snoozeloo.core.ui.headerbuttons.HeaderButtons
import com.plcoding.snoozeloo.core.ui.headerbuttons.SingleButtonState

@Composable
fun EditAlarmScreen(state: EditAlarmState, onEditAlarm: OnEditAlarm) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        HeaderButtons(state.headerButtonStates) { event ->
            when (event.buttonType) {
                HeaderButtonType.CLOSE -> onEditAlarm(EditAlarmEvent.CancelClicked)
                HeaderButtonType.SAVE -> onEditAlarm(EditAlarmEvent.SaveClicked)
                else -> Unit
//                HeaderButtonType.NO_BUTTON -> TODO()
//                HeaderButtonType.BACK_ARROW -> TODO()
            }
        }
        Spacer(Modifier.height(8.dp))
        EditableClockComponent(state.clockDigitStates, onEditAlarm)
        AlarmNameComponent()
    }

}