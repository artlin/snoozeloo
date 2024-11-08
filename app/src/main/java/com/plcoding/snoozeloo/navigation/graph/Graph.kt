package com.plcoding.snoozeloo.navigation.graph

sealed class Graph(val route: String) {
    data object Main : Graph("main_graph")
    // we can add other graphs if needed
}
