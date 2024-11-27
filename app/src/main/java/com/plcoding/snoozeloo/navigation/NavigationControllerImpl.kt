package com.plcoding.snoozeloo.navigation

import androidx.navigation.NavHostController
import com.plcoding.snoozeloo.navigation.route.NavigationRoute

class NavigationControllerImpl : NavigationController {

    private var navController: NavHostController? = null

    fun setNavController(navController: NavHostController) {
        this.navController = navController
    }

    override fun navigateTo(navRoute: NavigationRoute) {
        println("Debug - Attempting to navigate to: $navRoute")
        navController?.navigate(navRoute)
    }

    override fun navigateBack() {
        navController?.popBackStack()
    }
}
