package com.plcoding.snoozeloo.alarm_selection.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.snoozeloo.core.domain.entity.RingtoneEntity
import com.plcoding.snoozeloo.core.domain.value.RingtoneId
import com.plcoding.snoozeloo.core.ui.headerbuttons.ButtonsState
import com.plcoding.snoozeloo.core.ui.headerbuttons.HeaderButtonType
import com.plcoding.snoozeloo.core.ui.headerbuttons.HeaderButtons
import com.plcoding.snoozeloo.core.ui.headerbuttons.SingleButtonState
import com.plcoding.snoozeloo.core.ui.text.TextBodyStrong

@Composable
fun RingtoneListScreen(
    ringtones: List<RingtoneEntity>,
    selectedRingtone: RingtoneEntity,
    onRingtoneEvent: OnRingtoneEvent
) {
    HeaderButtons(
        ButtonsState(
            leftButton = SingleButtonState(buttonType = HeaderButtonType.BACK_ARROW),
            rightButton = SingleButtonState()
        ),
        onEvent = {})


    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(ringtones.size, key = ({ index -> ringtones[index].uri })) { index ->
            val ringtone = ringtones[index]
            RingtoneItemComponent(
                ringtone,
                isSelected = ringtone.uri.toString() == selectedRingtone.uri.toString(),
                onUriClick = {
                    onRingtoneEvent(RingtoneEvent.RingtoneSelected(RingtoneId(it)))
                })
        }
    }
}

@Composable
fun RingtoneItemComponent(
    ringtoneEntity: RingtoneEntity,
    isSelected: Boolean,
    onUriClick: OnUriClick
) {
    Row(
        Modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(10.dp)
            )
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 16.dp)
            .clickable { onUriClick(ringtoneEntity.uri) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        AlarmIcon()
        TextBodyStrong(text = ringtoneEntity.title.value, color = MaterialTheme.colorScheme.onSurface)
    }
}