package com.plcoding.snoozeloo.core.domain.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.plcoding.snoozeloo.core.domain.db.converters.Converters
import com.plcoding.snoozeloo.core.domain.db.dao.AlarmsDao

@Database(
    entities = [Alarm::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)

abstract class AlarmsDatabase: RoomDatabase() {

    abstract fun alarmsDao(): AlarmsDao

}