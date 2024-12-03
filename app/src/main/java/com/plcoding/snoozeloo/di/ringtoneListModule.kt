package com.plcoding.snoozeloo.di

import com.plcoding.snoozeloo.alarm_selection.domain.GetSystemRingtonesUseCase
import com.plcoding.snoozeloo.alarm_selection.presentation.RingtoneViewModel
import com.plcoding.snoozeloo.alarm_selection.presentation.RingtonesManager
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val ringtoneListModule = module {
    viewModel { params ->
        RingtoneViewModel(get(), get(), currentRingtone = params.get())
    }
    single { RingtonesManager(get()) }
    factory { GetSystemRingtonesUseCase(get()) }
}