package com.plcoding.snoozeloo.navigation.route

import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoute(
    val route: Route
) {
    // Main Flow Routes
    @Serializable
    data object Alarms : NavigationRoute(Route("alarms"))

    @Serializable
    data object EditAlarm : NavigationRoute(Route("edit_alarm"))
}

fun NavHostController.navigateRoute(
    route: Route,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    navigate(route.value, navOptions, navigatorExtras)
}