package com.plcoding.snoozeloo.manager.ui.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plcoding.snoozeloo.core.ui.text.TextBody
import com.plcoding.snoozeloo.core.ui.theme.SnoozelooTheme
import com.plcoding.snoozeloo.manager.domain.ClockAlarmDescriptionState
import com.plcoding.snoozeloo.manager.domain.ClockDigitStates

@Composable
fun ClockWithDescriptionComponent(
    state: ClockDigitStates,
    descriptionState: ClockAlarmDescriptionState,
    onEditAlarm: OnEditAlarm
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TimeComponent(state, onComponentClick = { clickType ->
            onEditAlarm(
                when (clickType) {
                    ComponentClickType.HOURS -> EditAlarmEvent.HoursComponentClicked
                    ComponentClickType.MINUTES -> EditAlarmEvent.MinutesComponentClicked
                }
            )
        }, onUserEnteredValue = { digit ->
            onEditAlarm(EditAlarmEvent.DigitEnteredFromKeyboard(digit))
        }, onKeyboardHidden = {
            onEditAlarm(EditAlarmEvent.KeyboardIsHidden)
        })
        if (descriptionState.isDescriptionEnabled) {
            TextBody(
                text = descriptionState.description,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(apiLevel = 33, device = Devices.PIXEL_4)
@Composable
fun PreviewEditableClockComponent() {
    SnoozelooTheme {
        val state = ClockDigitStates().asNewAlarm()
        ClockWithDescriptionComponent(state, ClockAlarmDescriptionState()) {

        }
    }
}