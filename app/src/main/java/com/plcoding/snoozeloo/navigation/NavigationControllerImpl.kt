package com.plcoding.snoozeloo.navigation

import androidx.navigation.NavHostController
import com.plcoding.snoozeloo.navigation.route.Route
import com.plcoding.snoozeloo.navigation.route.navigateRoute

class NavigationControllerImpl() : NavigationController {

    private var navController: NavHostController? = null

    fun setNavController(navController: NavHostController) {
        this.navController = navController
    }

    override fun navigateTo(route: Route) {
        navController?.navigateRoute(route)
    }

    override fun navigateBack() {
        navController?.popBackStack()
    }
}

