package com.plcoding.snoozeloo.di

import com.plcoding.snoozeloo.core.data.mapper.AlarmEntityMapper
import com.plcoding.snoozeloo.core.data.mapper.DataMapper
import com.plcoding.snoozeloo.core.domain.MainViewModel
import com.plcoding.snoozeloo.core.domain.db.Alarm
import com.plcoding.snoozeloo.core.domain.db.AlarmsDatabase
import com.plcoding.snoozeloo.core.domain.db.dao.AlarmsDao
import com.plcoding.snoozeloo.core.domain.db.getAlarmsDatabase
import com.plcoding.snoozeloo.manager.domain.AlarmEntity
import com.plcoding.snoozeloo.manager.domain.UpdateAlarmUseCase
import com.plcoding.snoozeloo.manager.domain.AlarmScheduler
import com.plcoding.snoozeloo.manager.domain.AlarmSchedulerImpl
import com.plcoding.snoozeloo.manager.ui.list.AlarmListViewModel
import com.plcoding.snoozeloo.manager.ui.edit.EditAlarmViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val coreModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::AlarmListViewModel)
    viewModel { params->
        EditAlarmViewModel(get(), get(), params.getOrNull())
    }
    single<AlarmsDatabase> { getAlarmsDatabase(androidContext()) }
    single<UpdateAlarmUseCase> { UpdateAlarmUseCase(get(), get()) }
    single<AlarmsDao> { get<AlarmsDatabase>().alarmsDao() }

    // mappers
    factory<DataMapper<Alarm, AlarmEntity>> { AlarmEntityMapper() }
    singleOf(::AlarmSchedulerImpl) { bind<AlarmScheduler>() }
}

