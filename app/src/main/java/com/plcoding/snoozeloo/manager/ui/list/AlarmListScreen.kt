package com.plcoding.snoozeloo.manager.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
}

@Composable
fun AlarmListHeader() {
    TextTitle1("Your alarms", Color(0xFF0D0F19))
}

