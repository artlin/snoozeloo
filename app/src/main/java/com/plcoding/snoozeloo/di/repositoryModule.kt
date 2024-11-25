package com.plcoding.snoozeloo.di

import com.plcoding.snoozeloo.core.domain.db.AlarmsDatabase
import com.plcoding.snoozeloo.core.domain.db.dao.AlarmsDao
import com.plcoding.snoozeloo.core.domain.db.getAlarmsDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single<AlarmsDatabase> { getAlarmsDatabase(androidContext()) }
    single<AlarmsDao> { get<AlarmsDatabase>().alarmsDao() }
}