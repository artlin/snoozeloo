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
import androidx.compose.ui.unit.dp

@Composable
fun EditableClockComponent(state: TimeComponentState, onEditAlarm: OnEditAlarm) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(10.dp))
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
        })

//        TextBody(
//            text = "Alarm in Xh Ymin",
//            color = Color(0xFF858585)
//        )
    }
}
