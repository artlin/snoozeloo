package com.plcoding.snoozeloo.manager.ui.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity
import com.plcoding.snoozeloo.core.domain.entity.ClockTime
import com.plcoding.snoozeloo.core.domain.value.AlarmName
import com.plcoding.snoozeloo.core.domain.value.TimeValue
import com.plcoding.snoozeloo.core.ui.theme.SnoozelooTheme

@Composable
fun AlarmListScreen(state: AlarmListState, onAlarmList: OnAlarmList) {

    if (state.list.isEmpty()) EmptyScreen()
    else {
        AlarmList(currentTime = { state.currentTime }, list = state.list, onAlarmList)
    }
    AddAlarmButton {
        onAlarmList(AlarmListEvent.AddAlarmClicked)
    }
}


@Preview(apiLevel = 33, device = Devices.PIXEL_4, widthDp = 360)
@Composable
private fun PreviewAlarmList() {
    SnoozelooTheme {
        AlarmList(
            currentTime = { TimeValue(System.currentTimeMillis()) },
            list = generateAlarms(),
            onAlarmList = {}
        )
    }
}

fun generateAlarms(): List<AlarmEntity> {
    val alarmNames = listOf(
        "WORK",
        "RUN",
        "WALK",
        "WAKEUP",
        "REST",
        "WORK",
        "RUN",
        "WALK",
        "WAKEUP",
        "REST"
    )
    val currentTime = System.currentTimeMillis() // Get the current timestamp
    val alarms = mutableListOf<AlarmEntity>()

    for (i in 0 until 10) {
        val alarmTime = currentTime + (i * 30 * 60 * 1000) // Increment by 30 minutes for each alarm
        alarms.add(
            AlarmEntity(
                uid = 3,
                alarmName = AlarmName(alarmNames[i]), // Alarm name
                isEnabled = (i % 2 == 0), // Enable only even-indexed alarms
                ringtoneId = "4", // Replace with actual ringtone ID
                isVibrateEnabled = true, // Enable vibration for all alarms
                volume = 0.5f, // Set volume to 0.5
                clockTime = ClockTime(30, 1),
                days = listOf(true, false, true, false, true, false, true), // Replace with actual values for days of the week
            )
        )
    }

    return alarms
}