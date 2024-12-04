package com.plcoding.snoozeloo.navigation

import androidx.navigation.NavHostController
import com.plcoding.snoozeloo.navigation.route.NavigationRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

interface NavigationController {
    fun getCurrentNavController(): NavHostController?
    fun navigateTo(navRoute: NavigationRoute)
    fun navigateBack()
    fun <T> setStateHandleValue(value: T, key: String)

    fun <T> getStateHandleValue(value: T, key: String)
    fun <T> CoroutineScope.watchStateHandle(
        key: String,
        defaultValue: T?,
        callBack: suspend (T?) -> Unit
    ): Job
}