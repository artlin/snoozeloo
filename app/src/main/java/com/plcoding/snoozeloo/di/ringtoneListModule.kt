package com.plcoding.snoozeloo.di

import com.plcoding.snoozeloo.alarm_selection.domain.GetSystemRingtonesUseCase
import com.plcoding.snoozeloo.alarm_selection.ui.RingtoneViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val ringtoneListModule = module {
    viewModel { params ->
        RingtoneViewModel(get(), get(), currentRingtone = params.get())
    }
    factory { GetSystemRingtonesUseCase(get()) }
}