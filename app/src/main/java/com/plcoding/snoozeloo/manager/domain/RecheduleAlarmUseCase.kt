package com.plcoding.snoozeloo.manager.domain

import com.plcoding.snoozeloo.core.data.mapper.DataMapper
import com.plcoding.snoozeloo.core.domain.db.Alarm
import com.plcoding.snoozeloo.core.domain.db.dao.AlarmsDao
import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity
import com.plcoding.snoozeloo.scheduler.AlarmScheduler

class RescheduleAlarmUseCase(
    private val alarmsDao: AlarmsDao,
    private val mapper: DataMapper<Alarm, AlarmEntity>,
    private val alarmScheduler: AlarmScheduler
) {
//    suspend operator fun invoke(alarmEntity: AlarmEntity) {
//        mapper.convert(alarmEntity)?.let { alarm ->
//            alarmsDao.upsert(alarm)
//        }

    suspend operator fun invoke(alarm: Alarm) {
        alarmScheduler.cancelAlarm(alarm)
        alarmScheduler.scheduleAlarm(alarm)

//        alarmScheduler.scheduleAlarm(alarmEntity)

//        mapper.convert(alarmEntity)?.let { alarm ->
//            alarmsDao.upsert(alarm)
//        }

//        alarmsDao.getAll()
//            .collect { alarms ->
//                alarms.map { alarm ->
////                    mapper.convert(alarm)
//
//                    alarmScheduler.scheduleAlarm(alarm)
////                    alarmScheduler.cancelAlarm(alarm)
//
//                }
//            }
    }
}
