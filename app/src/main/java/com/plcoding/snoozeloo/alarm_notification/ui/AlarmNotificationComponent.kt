package com.plcoding.snoozeloo.alarm_notification.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.plcoding.snoozeloo.R
import com.plcoding.snoozeloo.core.ui.text.TextH1
import com.plcoding.snoozeloo.core.ui.text.TextTitle1Strong

@Composable
fun AlarmNotificationComponent() {
    Column(
        Modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.CenterVertically)
    ) {
        Icon(
            modifier = Modifier.size(62.dp),
            painter = painterResource(id = R.drawable.icon_alarm),
            contentDescription = null,
            tint = Color(0xFF4664FF)
        )
        TextH1(text = "10:00", color = Color(0xFF4664FF))
        TextTitle1Strong(text = "WORK", color = Color(0xFF0D0F19))
        Button(onClick = {}) {
            TextTitle1Strong(text = "Turn Off", color = Color.White)
        }
    }
}