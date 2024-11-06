package com.plcoding.snoozeloo.manager.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.plcoding.snoozeloo.navigation.NavigationController
import com.plcoding.snoozeloo.navigation.route.NavigationRoute
import com.plcoding.snoozeloo.navigation.route.navigateRoute
import org.koin.compose.koinInject

@Composable
fun AlarmListScreen() {
    // todo : only for testing purpose, navigation controller should be moved into viewModel
    val navigation = koinInject<NavigationController>()
    Box(
        Modifier
            .background(Color.Green)
            .size(100.dp)
            .clickable {
                navigation.navigateTo(NavigationRoute.EditAlarm.route)
            })
}