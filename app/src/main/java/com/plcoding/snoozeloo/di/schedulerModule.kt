package com.plcoding.snoozeloo.di

import com.plcoding.snoozeloo.scheduler.AlarmScheduler
import com.plcoding.snoozeloo.scheduler.AlarmSchedulerImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val schedulerModule = module {
    singleOf(::AlarmSchedulerImpl) { bind<AlarmScheduler>() }
}