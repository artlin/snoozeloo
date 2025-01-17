package com.plcoding.snoozeloo.alarm_notification.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plcoding.snoozeloo.R
import com.plcoding.snoozeloo.core.domain.LockScreenAlarmEvent
import com.plcoding.snoozeloo.core.domain.LockScreenAlarmState
import com.plcoding.snoozeloo.core.domain.OnLockScreenAlarm
import com.plcoding.snoozeloo.core.ui.text.TextH1
import com.plcoding.snoozeloo.core.ui.text.TextTitle1Strong
import java.util.Locale


@Composable
fun AlarmNotificationComponent(
    state: LockScreenAlarmState,
    onLockScreenAlarm: OnLockScreenAlarm
) {
    val localeList = LocalContext.current.resources.configuration.getLocales()
    val applicationLocale = if (localeList.isEmpty) Locale.getDefault() else localeList[0]
    val formatedTime by remember(state.alarm?.clockTime) {
        derivedStateOf {
            String.format(
                applicationLocale,
                "%02d:%02d",
                state.alarm?.clockTime?.hours,
                state.alarm?.clockTime?.minutes
            )
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.CenterVertically)
    ) {
        Icon(
            modifier = Modifier.size(62.dp),
            painter = painterResource(id = R.drawable.icon_alarm),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        TextH1(
            text = formatedTime,
            color = MaterialTheme.colorScheme.primary)
        TextTitle1Strong(
            text = state.alarm?.alarmName?.value.toString(),
            color = MaterialTheme.colorScheme.onBackground)
        Button(
            onClick = {
                onLockScreenAlarm(LockScreenAlarmEvent.CloseClicked)
            }
        ) {
            TextTitle1Strong(text = "Turn Off", color = MaterialTheme.colorScheme.onPrimary)
        }
        Button(
            onClick = {
                onLockScreenAlarm(LockScreenAlarmEvent.SnoozeClicked)
            }
        ) {
            TextTitle1Strong(text = "Snooze", color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

