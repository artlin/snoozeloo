package com.plcoding.snoozeloo.alarm_vibrate.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.snoozeloo.alarm_vibrate.presentation.AlarmVibrateSubState
import com.plcoding.snoozeloo.core.ui.CustomSwitch
import com.plcoding.snoozeloo.core.ui.text.TextTitle2Strong

@Composable
fun AlarmVibrateComponent(
    label: String,
    state: AlarmVibrateSubState,
    onEvent: (Boolean) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextTitle2Strong(text = label, color = MaterialTheme.colorScheme.onSurface)
        CustomSwitch(isEnabled = state.isVibrateEnabled, onEvent = onEvent)
    }
}

//val checkedThumbColor: Color,
//val checkedTrackColor: Color,
//val checkedBorderColor: Color,
//val checkedIconColor: Color,
//val uncheckedThumbColor: Color,
//val uncheckedTrackColor: Color,
//val uncheckedBorderColor: Color,
//val uncheckedIconColor: Color,
//val disabledCheckedThumbColor: Color,
//val disabledCheckedTrackColor: Color,
//val disabledCheckedBorderColor: Color,
//val disabledCheckedIconColor: Color,
//val disabledUncheckedThumbColor: Color,
//val disabledUncheckedTrackColor: Color,
//val disabledUncheckedBorderColor: Color,
//val disabledUncheckedIconColor: Color
