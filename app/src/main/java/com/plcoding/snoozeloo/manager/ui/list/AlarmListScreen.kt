package com.plcoding.snoozeloo.manager.ui.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.plcoding.snoozeloo.R
import com.plcoding.snoozeloo.core.ui.FAB
import com.plcoding.snoozeloo.core.ui.text.TextTitle1
import com.plcoding.snoozeloo.core.ui.text.TextTitle2

@Composable
fun AlarmListScreen(state: AlarmListState, onAlarmList: OnAlarmList) {
    AlarmListHeader()
    if (state.list.isEmpty()) EmptyScreen()
    AddAlarmButton {
        onAlarmList(AlarmListEvent.AddAlarmClicked)
    }
}

@Composable
private fun AddAlarmButton(onClick: () -> Unit) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        AnimatedVisibility(
            visible = true,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut(),
            modifier = Modifier
                .padding(bottom = 16.dp)
        ) {
            FAB(Modifier.clickable {
                onClick()
            })
        }
    }
}

@Composable
fun AlarmListHeader() {
    TextTitle1(text = "Your alarms", color = MaterialTheme.colorScheme.onSurface)
}

@Composable
fun EmptyScreen() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(35.dp, alignment = Alignment.CenterVertically)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_alarm),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        TextTitle2(
            text = "It's empty! Add the first alarm so you don't miss an important moment!",
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
    }
}