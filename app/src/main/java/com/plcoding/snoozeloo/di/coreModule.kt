package com.plcoding.snoozeloo.di

import com.plcoding.snoozeloo.core.data.mapper.AlarmEntityMapper
import com.plcoding.snoozeloo.core.data.mapper.DataMapper
import com.plcoding.snoozeloo.core.domain.MainViewModel
import com.plcoding.snoozeloo.core.domain.db.Alarm
import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity
import com.plcoding.snoozeloo.scheduler.AlarmScheduler
import com.plcoding.snoozeloo.scheduler.AlarmSchedulerImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val coreModule = module {
    viewModelOf(::MainViewModel)
    // mappers
    factory<DataMapper<Alarm, AlarmEntity>> { AlarmEntityMapper() }
}

