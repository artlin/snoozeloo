package com.plcoding.snoozeloo.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun RootGraph(
    navController: NavHostController,
    startDestination: String = Graph.Main.route
) {
    NavHost(
        navController = navController,
        route = Graph.Root.route,
        startDestination = startDestination
    ) {
        // Main Graph
        mainGraph()
    }
}