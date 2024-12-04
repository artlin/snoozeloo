package com.plcoding.snoozeloo.manager.ui.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.snoozeloo.alarm_selection.presentation.ui.getLabelShort
import com.plcoding.snoozeloo.core.ui.headerbuttons.HeaderButtonType
import com.plcoding.snoozeloo.core.ui.headerbuttons.HeaderButtons

@Composable
fun EditAlarmScreen(state: UIStateEditAlarm, onEditAlarm: OnEditAlarm) {
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
        ClockWithDescriptionComponent(state.clockDigitStates, state.clockDescription, onEditAlarm)
        ClickableRowWithLabelComponent(
            "Alarm name",
            state.alarmNameSubState.name.value,
            onClick = { onEditAlarm(EditAlarmEvent.ChangeAlarmNameClicked) })
        ClickableRowWithLabelComponent(
            "Alarm ringtone",
            state.selectedRingtoneEntity.getLabelShort(),
            onClick = { onEditAlarm(EditAlarmEvent.SelectRingtoneClicked) })
    }
    if (state.alarmNameSubState.isDialogShown) {
        EditAlarmNameDialog(state.alarmNameSubState.editedName.value, onEditAlarm)
    }
}
