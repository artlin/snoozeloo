package com.plcoding.snoozeloo.core.domain.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.plcoding.snoozeloo.core.domain.db.Alarm
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmsDao {

    @Upsert
    suspend fun upsert(alarm: Alarm)

    @Delete
    suspend fun delete(alarm: Alarm)

    @Query("SELECT * FROM alarm")
    fun getAll(): Flow<List<Alarm>>
}