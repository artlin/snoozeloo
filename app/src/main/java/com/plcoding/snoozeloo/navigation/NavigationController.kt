package com.plcoding.snoozeloo.navigation

import com.plcoding.snoozeloo.navigation.route.Route

interface NavigationController {
    fun navigateTo(route: Route)
    fun navigateBack()
}