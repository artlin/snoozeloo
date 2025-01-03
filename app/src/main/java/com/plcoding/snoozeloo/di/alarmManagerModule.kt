package com.plcoding.snoozeloo.di

import com.plcoding.snoozeloo.manager.domain.RescheduleAlarmUseCase
import com.plcoding.snoozeloo.manager.domain.RescheduleAllAlarmsUseCase
import com.plcoding.snoozeloo.manager.domain.UpdateAlarmUseCase
import com.plcoding.snoozeloo.manager.ui.edit.EditAlarmViewModel
import com.plcoding.snoozeloo.manager.ui.list.AlarmListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val alarmManagerModule = module {
    viewModelOf(::AlarmListViewModel)
    single<RescheduleAllAlarmsUseCase> { RescheduleAllAlarmsUseCase(get(), get(), get()) }
    single<RescheduleAlarmUseCase> { RescheduleAlarmUseCase(get(), get(), get()) }

    single<UpdateAlarmUseCase> { UpdateAlarmUseCase(get(), get(), get()) }
    viewModel { params ->
        EditAlarmViewModel(
            updateAlarmUseCase = get(),
            navigationController = get(),
            ringtonesManager = get(),
            savedStateHandle = params[1],
            alarmEntityArgument = params[0]
        )
    }
}