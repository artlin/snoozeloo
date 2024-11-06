package com.plcoding.snoozeloo.di

import com.plcoding.snoozeloo.navigation.NavigationController
import com.plcoding.snoozeloo.navigation.NavigationControllerImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val navigationModule = module {
    singleOf(::NavigationControllerImpl) { bind<NavigationController>() }
}
