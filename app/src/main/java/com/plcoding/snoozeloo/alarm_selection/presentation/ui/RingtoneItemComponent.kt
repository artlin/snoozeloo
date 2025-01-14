package com.plcoding.snoozeloo.alarm_selection.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.snoozeloo.alarm_selection.presentation.OnUriClick
import com.plcoding.snoozeloo.core.domain.entity.RingtoneEntity
import com.plcoding.snoozeloo.core.domain.entity.SpecialRingtoneType
import com.plcoding.snoozeloo.core.ui.text.TextBodyStrong

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
        LeftSideIcon(ringtoneEntity.specialType)
        TextBodyStrong(
            text = ringtoneEntity.getLabel(),
            color = MaterialTheme.colorScheme.onSurface
        )
        if (isSelected) {
            Spacer(Modifier.weight(1f))
            SelectionIndicator()
        }
    }
}

@Composable
private fun LeftSideIcon(specialType: SpecialRingtoneType) {
    when (specialType) {
        SpecialRingtoneType.MUTE -> AlarmIconCrossed()
        SpecialRingtoneType.NONE, SpecialRingtoneType.DEFAULT -> AlarmIcon()
    }
}