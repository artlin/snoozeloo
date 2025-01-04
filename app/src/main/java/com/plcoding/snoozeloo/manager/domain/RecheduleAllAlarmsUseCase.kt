package com.plcoding.snoozeloo.manager.domain

import com.plcoding.snoozeloo.core.data.mapper.DataMapper
import com.plcoding.snoozeloo.core.domain.db.Alarm
import com.plcoding.snoozeloo.core.domain.db.dao.AlarmsDao
import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity
import com.plcoding.snoozeloo.scheduler.AlarmScheduler

class RescheduleAllAlarmsUseCase(
    private val alarmsDao: AlarmsDao,
    private val mapper: DataMapper<Alarm, AlarmEntity>,
    private val alarmScheduler: AlarmScheduler
) {
    suspend operator fun invoke() {
        alarmsDao.getAll()
            .collect { alarms ->
                alarms.map { alarm ->
                    alarmScheduler.scheduleAlarm(alarm)
                }
            }
    }
}
