package com.plcoding.snoozeloo.core.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun CustomSwitch(isEnabled: Boolean, onEvent: (Boolean) -> Unit) {
    Switch(
        isEnabled, {
            onEvent(it)
        }, colors = SwitchDefaults.colors().copy(
            checkedThumbColor = MaterialTheme.colorScheme.surface,
            checkedTrackColor = MaterialTheme.colorScheme.primary,
            uncheckedThumbColor = MaterialTheme.colorScheme.surface,
            uncheckedTrackColor = Color(0xFFBCC6FF),
            uncheckedBorderColor = Color(0xFFBCC6FF)

        )
    )
}