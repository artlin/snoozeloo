package com.plcoding.snoozeloo.core.domain.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.plcoding.snoozeloo.core.domain.db.dao.AlarmsDao

@Database(
    entities = [Alarm::class],
    version = 1,
    exportSchema = false
)

abstract class AlarmsDatabase: RoomDatabase() {

    abstract fun alarmsDao(): AlarmsDao

}