package com.plcoding.snoozeloo.navigation

import androidx.navigation.NavHostController
import com.plcoding.snoozeloo.navigation.route.NavigationRoute

class NavigationControllerImpl : NavigationController {

    private var navController: NavHostController? = null

    override fun getCurrentNavController(): NavHostController? = navController


    fun setNavController(navController: NavHostController) {
        this.navController = navController
    }

    override fun navigateTo(navRoute: NavigationRoute) {
        navController?.navigate(navRoute)
    }

    override fun navigateBack() {
        navController?.navigateUp()
    }

    override fun <T> setStateHandleValue(value: T, key: String) {
        navController?.previousBackStackEntry?.savedStateHandle?.set(key, value)
    }
}
