package com.plcoding.snoozeloo.core.domain.db

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

fun getAlarmsDatabase(context: Context): AlarmsDatabase {
    val dbFile = context.getDatabasePath("alarms.db")
    return Room.databaseBuilder<AlarmsDatabase>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    )
        .setDriver(BundledSQLiteDriver())
        .fallbackToDestructiveMigration(dropAllTables = true)
        .build()

}