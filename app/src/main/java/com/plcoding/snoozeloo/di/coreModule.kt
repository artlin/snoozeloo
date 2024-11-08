package com.plcoding.snoozeloo.di

import com.plcoding.snoozeloo.core.domain.MainViewModel
import com.plcoding.snoozeloo.manager.ui.list.AlarmListViewModel
import com.plcoding.snoozeloo.manager.ui.edit.EditAlarmViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val coreModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::AlarmListViewModel)
    viewModelOf(::EditAlarmViewModel)
}