package com.plcoding.snoozeloo.di

import androidx.navigation.NavBackStackEntry
import com.plcoding.snoozeloo.manager.domain.UpdateAlarmUseCase
import com.plcoding.snoozeloo.manager.ui.edit.EditAlarmViewModel
import com.plcoding.snoozeloo.manager.ui.list.AlarmListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val alarmManagerModule = module {
    viewModelOf(::AlarmListViewModel)
    single<UpdateAlarmUseCase> { UpdateAlarmUseCase(get(), get()) }
    viewModel { params ->
        EditAlarmViewModel(
            ringtonesUseCase = get(),
            updateAlarmUseCase = get(),
            navigationController = get(),
            savedStateHandle = params[1],
            alarmEntityArgument = params[0]
        )
    }
}