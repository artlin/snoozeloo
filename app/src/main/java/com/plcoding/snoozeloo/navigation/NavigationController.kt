package com.plcoding.snoozeloo.navigation

import androidx.navigation.NavHostController
import com.plcoding.snoozeloo.navigation.route.NavigationRoute

interface NavigationController {
    fun getCurrentNavController(): NavHostController?
    fun navigateTo(navRoute: NavigationRoute)
    fun navigateBack()
    fun <T> setStateHandleValue(value: T, key: String)
}