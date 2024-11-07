package com.plcoding.snoozeloo.navigation.graph

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun RootGraph(
    navController: NavHostController,
    startDestination: String = Graph.Main.route
) {
    NavHost(
        modifier = Modifier.padding(top = 64.dp, start = 16.dp, end = 16.dp),
        navController = navController,
        route = Graph.Root.route,
        startDestination = startDestination
    ) {
        // Main Graph
        mainGraph()
    }
}