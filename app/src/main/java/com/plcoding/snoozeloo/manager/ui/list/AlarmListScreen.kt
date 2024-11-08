package com.plcoding.snoozeloo.manager.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.plcoding.snoozeloo.R
import com.plcoding.snoozeloo.core.ui.text.TextTitle1
import com.plcoding.snoozeloo.core.ui.text.TextTitle2
import com.plcoding.snoozeloo.navigation.NavigationController
import com.plcoding.snoozeloo.navigation.route.NavigationRoute
import org.koin.compose.koinInject

@Composable
fun AlarmListScreen() {
    // todo : only for testing purpose, navigation controller should be moved into viewModel
    val navigation = koinInject<NavigationController>()
    AlarmListHeader()
    EmptyScreen()
}

@Composable
fun AlarmListHeader() {
    TextTitle1(text = "Your alarms", color = Color(0xFF0D0F19))
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
            tint = Color(0xFF4664FF)

        )
        TextTitle2(
            text = "It's empty! Add the first alarm so you don't miss an important moment!",
            color = Color(0xFF0D0F19)
        )
    }
}