package com.plcoding.snoozeloo.manager.ui.list

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.plcoding.snoozeloo.core.ui.text.TextTitle1

@Composable
fun AlarmListHeader() {
    TextTitle1(text = "Your alarms", color = MaterialTheme.colorScheme.onSurface)
}