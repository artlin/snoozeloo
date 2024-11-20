package com.plcoding.snoozeloo.manager.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plcoding.snoozeloo.core.domain.getAlarmInTime
import com.plcoding.snoozeloo.core.domain.getAlarmTime
import com.plcoding.snoozeloo.core.ui.text.TextBody
import com.plcoding.snoozeloo.core.ui.text.TextH3
import com.plcoding.snoozeloo.core.ui.text.TextTitle2Strong
import com.plcoding.snoozeloo.core.ui.theme.SnoozelooTheme
import com.plcoding.snoozeloo.manager.domain.TimeValue
import com.plcoding.snoozeloo.manager.ui.edit.OnClick
import com.plcoding.snoozeloo.manager.ui.edit.OnClickWithValue
import java.util.UUID

@Composable
fun AlarmListScreen(state: AlarmListState, onAlarmList: OnAlarmList) {
    AlarmListHeader()
    if (state.list.isEmpty()) EmptyScreen()
    else AlarmList(currentTime = state.currentTime, list = state.list, onAlarmList)
    AddAlarmButton {
        onAlarmList(AlarmListEvent.AddAlarmClicked)
    }
}

@Composable
fun AlarmList(currentTime: TimeValue, list: List<AlarmEntity>, onAlarmList: OnAlarmList) {
    LazyColumn(modifier = Modifier.padding(12.dp), verticalArrangement = spacedBy(16.dp)) {
        items(items = list,
            key = { item -> item.uid }) { item ->
            AlarmItem(alarmEntity = item,
                currentTime = currentTime,
                onToggleClick = { onAlarmList(AlarmListEvent.ToggleAlarmClicked(item.uid)) },
                onCardClick = { onAlarmList(AlarmListEvent.AlarmCardClicked(item.uid)) })
        }
    }
}


@Composable
fun AlarmItem(
    alarmEntity: AlarmEntity,
    currentTime: TimeValue,
    onToggleClick: OnClick,
    onCardClick: OnClickWithValue
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(16.dp), contentAlignment = Alignment.TopEnd
    ) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = spacedBy(10.dp)) {
            TextTitle2Strong(
                text = alarmEntity.alarmName,
                color = MaterialTheme.colorScheme.onSurface
            )
            TextH3(
                text = getAlarmTime(alarmEntity.alarmTime).toAlarmTime(),
                color = MaterialTheme.colorScheme.onSurface
            )
            TextBody(
                text = getAlarmInTime(currentTime, alarmEntity.alarmTime).toAlarmInTime(),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Switch(
            alarmEntity.isEnabled, {}, colors = SwitchDefaults.colors().copy(

            )
        )
    }
}

private fun Pair<Int, Int>.toAlarmTime(): String {
    return StringBuilder().apply {
        append("$first")
        append(":")
        if(second<10)append("0")
        append("$second")
    }.toString()
}

private fun Pair<Long, Long>.toAlarmInTime(): String {
    return StringBuilder().apply {
        append("Alarm in")
        if (first > 0) append(" ${first}h")
        if(second<10)append(" 0")
        else append(" ")
        append("$second")
        append("min")
    }.toString()
}

@Preview(apiLevel = 33, device = Devices.PIXEL_4, widthDp = 360)
@Composable
private fun PreviewAlarmList() {
    SnoozelooTheme {
        val listOfAlarms = generateAlarms(
            listOf(
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
        )
        AlarmList(
            currentTime = TimeValue(System.currentTimeMillis()),
            list = listOfAlarms,
            onAlarmList = {}
        )
    }
}

fun generateAlarms(alarmNames: List<String>): List<AlarmEntity> {
    val currentTime = System.currentTimeMillis() // Get the current timestamp
    val alarms = mutableListOf<AlarmEntity>()

    for (i in 0 until 10) {
        val alarmTime = currentTime + (i * 30 * 60 * 1000) // Increment by 30 minutes for each alarm
        alarms.add(
            AlarmEntity(
                uid = UUID.randomUUID().toString(), // Generate a random unique ID
                alarmName = alarmNames[i], // Alarm name
                alarmTime = TimeValue(alarmTime), // Set calculated time
                isEnabled = (i % 2 == 0) // Enable only even-indexed alarms
            )
        )
    }

    return alarms
}