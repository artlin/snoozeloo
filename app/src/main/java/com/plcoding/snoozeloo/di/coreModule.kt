package com.plcoding.snoozeloo.di

import com.plcoding.snoozeloo.core.data.mapper.AlarmEntityMapper
import com.plcoding.snoozeloo.core.data.mapper.DataMapper
import com.plcoding.snoozeloo.core.domain.MainViewModel
import com.plcoding.snoozeloo.core.domain.db.Alarm
import com.plcoding.snoozeloo.core.domain.db.AlarmsDatabase
import com.plcoding.snoozeloo.core.domain.db.dao.AlarmsDao
import com.plcoding.snoozeloo.core.domain.db.getAlarmsDatabase
import com.plcoding.snoozeloo.manager.domain.AlarmEntity
import com.plcoding.snoozeloo.manager.ui.list.AlarmListViewModel
import com.plcoding.snoozeloo.manager.ui.edit.EditAlarmViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val coreModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::AlarmListViewModel)
    viewModelOf(::EditAlarmViewModel)

    single<AlarmsDatabase> { getAlarmsDatabase(androidContext()) }

    single<AlarmsDao> { get<AlarmsDatabase>().alarmsDao() }

    // mappers
    factory<DataMapper<AlarmEntity, Alarm>> { AlarmEntityMapper() }
}

