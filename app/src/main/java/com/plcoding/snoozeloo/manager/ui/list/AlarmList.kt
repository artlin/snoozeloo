package com.plcoding.snoozeloo.manager.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity
import com.plcoding.snoozeloo.core.domain.value.TimeValue

@Composable
fun AlarmList(currentTime: () -> TimeValue, list: List<AlarmEntity>, onAlarmList: OnAlarmList) {
    println("Alarm list recompose")
    LazyColumn(
        modifier = Modifier.padding(12.dp),
        verticalArrangement = Arrangement.Absolute.spacedBy(16.dp)
    ) {
        item {
            AlarmListHeader()
        }

        items(items = list,
            key = { item -> item.uid }) { item ->
            AlarmItem(alarmEntity = { item },
                currentTime = currentTime,
                onToggleClick = { onAlarmList(AlarmListEvent.ToggleAlarmClicked(item.uid)) },
                onCardClick = { onAlarmList(AlarmListEvent.AlarmCardClicked(item)) })
        }
    }
}