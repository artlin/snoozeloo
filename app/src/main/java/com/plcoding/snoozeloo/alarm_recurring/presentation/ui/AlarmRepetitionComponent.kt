package com.plcoding.snoozeloo.alarm_recurring.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plcoding.snoozeloo.alarm_recurring.presentation.AlarmRepetitionSubState
import com.plcoding.snoozeloo.core.ui.text.TextLabel
import com.plcoding.snoozeloo.core.ui.text.TextTitle2Strong
import com.plcoding.snoozeloo.core.ui.theme.SnoozelooTheme
import com.plcoding.snoozeloo.manager.ui.edit.OnClick
import java.text.DateFormatSymbols

@Composable
fun AlarmRepetitionComponent(
    label: String,
    state: AlarmRepetitionSubState,
    onClickedIndex: (Int) -> Unit,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TextTitle2Strong(text = label, color = MaterialTheme.colorScheme.onSurface)
        DayButtonsSelector(state.selected, onClickedIndex, true)
    }
}

@Composable
fun DayButtonsSelector(
    selectedDays: List<Boolean>,
    onClickedIndex: (Int) -> Unit,
    isClickable: Boolean
) {
    val locale = Locale.current.platformLocale
    val daysOfWeek = DateFormatSymbols(locale).shortWeekdays
        .drop(1) // Remove empty first element at index 0
        .map { it.take(1).uppercase(locale) }

    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        daysOfWeek.forEachIndexed { index, dayLabel ->
            DayButton(
                label = dayLabel,
                isSelected = selectedDays.getOrNull(index) ?: false,
                onClick = if (isClickable) ({ onClickedIndex(index) }) else null
            )
        }
    }
}

@Composable
fun DayButton(label: String, isSelected: Boolean, onClick: OnClick? = null) {
    val optionalClickModifier = if(onClick==null) Modifier else Modifier.clickable { onClick() }
    val buttonModifier = Modifier
        .background(
            color = if (isSelected) MaterialTheme.colorScheme.primary else Color(0xFFECEFFF),
            shape = RoundedCornerShape(38.dp)
        )
        .widthIn(min = 38.dp)
        .clip(RoundedCornerShape(38.dp))
        .padding(vertical = 6.dp, horizontal = 11.dp)
        .then(optionalClickModifier)
    val textModifier = Modifier
    val textColor: Color = if (isSelected) MaterialTheme.colorScheme.surface else Color(0xFF0D0F19)
    Box(buttonModifier, contentAlignment = Alignment.Center) {
        TextLabel(textModifier, label, textColor)
    }
}

@Preview(device = Devices.PIXEL_4)
@Composable
fun PreviewDayButtonSelector() {
    SnoozelooTheme {
        DayButtonsSelector(emptyList(), {}, true)
    }
}


@Preview(device = Devices.PIXEL_4)
@Composable
fun PreviewDayButton() {
    SnoozelooTheme {
        DayButton("M", true, {})
    }
}


@Preview(device = Devices.PIXEL_4)
@Composable
fun PreviewAlarmRepetitionComponent() {
    val state = AlarmRepetitionSubState.getDefault()
    SnoozelooTheme {
        AlarmRepetitionComponent("Repeat", state, {})
    }
}