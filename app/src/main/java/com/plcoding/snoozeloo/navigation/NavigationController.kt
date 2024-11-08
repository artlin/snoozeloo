package com.plcoding.snoozeloo.navigation

import com.plcoding.snoozeloo.navigation.route.NavigationRoute

interface NavigationController {
    fun navigateTo(navRoute: NavigationRoute)
    fun navigateBack()
}