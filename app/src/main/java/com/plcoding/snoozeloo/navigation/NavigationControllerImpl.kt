package com.plcoding.snoozeloo.navigation

import androidx.navigation.NavHostController
import com.plcoding.snoozeloo.navigation.route.NavigationRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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

    override fun <T> getStateHandleValue(value: T, key: String) {

    }

    override fun <T> CoroutineScope.watchStateHandle(
        key: String,
        defaultValue: T?,
        callBack: suspend (T?) -> Unit
    ): Job {
        return launch {
            val handle = navController?.currentBackStackEntry?.savedStateHandle
            handle?.getStateFlow(key, defaultValue)
                ?.collect { value ->
                    callBack(value)
                    handle[key] = null
                } ?: callBack(defaultValue)
        }
    }
}
