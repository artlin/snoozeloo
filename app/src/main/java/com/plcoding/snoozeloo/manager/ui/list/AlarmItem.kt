package com.plcoding.snoozeloo.manager.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.snoozeloo.core.common.extension.toAlarmInTime
import com.plcoding.snoozeloo.core.common.extension.toAlarmTime
import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity
import com.plcoding.snoozeloo.core.domain.getTimeLeftToAlarm
import com.plcoding.snoozeloo.core.domain.value.TimeValue
import com.plcoding.snoozeloo.core.ui.text.TextBody
import com.plcoding.snoozeloo.core.ui.text.TextH3
import com.plcoding.snoozeloo.core.ui.text.TextTitle2Strong
import com.plcoding.snoozeloo.manager.ui.edit.OnClick
import com.plcoding.snoozeloo.manager.ui.edit.OnClickWithIntValue

@Composable
fun AlarmItem(
    alarmEntity: () -> AlarmEntity,
    currentTime: () -> TimeValue,
    onToggleClick: OnClick,
    onCardClick: OnClickWithIntValue
) {
    println("AlarmItem recomposed") // or Log.d()

    val entity = remember(alarmEntity) { alarmEntity() }
    val time = remember(currentTime) { currentTime() }

    val alarmTimeString = remember(entity.hours, entity.minutes) {
        println("alarmTimeString recalculated")
        Pair(entity.hours, entity.minutes).toAlarmTime()
    }

    val alarmInTimeString = remember(time, entity.hours, entity.minutes) {
        println("alarmInTime recalculated")
        getTimeLeftToAlarm(time, entity.hours, entity.minutes).toAlarmInTime()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(16.dp)
            .clickable {
                onCardClick(entity.uid)
            }, contentAlignment = Alignment.TopEnd
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Absolute.spacedBy(10.dp)
        ) {
            TextTitle2Strong(
                text = entity.alarmName.value,
                color = MaterialTheme.colorScheme.onSurface
            )
            TextH3(
                text = alarmTimeString,
                color = MaterialTheme.colorScheme.onSurface
            )
            TextBody(
                text = alarmInTimeString,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Switch(
            entity.isEnabled, {}, colors = SwitchDefaults.colors().copy(

            )
        )
    }
}

