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
import com.plcoding.snoozeloo.core.domain.navigation.Graph
import com.plcoding.snoozeloo.core.domain.navigation.NavigationRoute

@Composable
fun AlarmListScreen(navController: NavHostController) {
    Box(Modifier.background(Color.Green).size(100.dp).clickable {
        navController.navigate(NavigationRoute.EditAlarm.route)
    })
}