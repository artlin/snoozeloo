package com.plcoding.snoozeloo.manager.domain

import com.plcoding.snoozeloo.core.data.mapper.DataMapper
import com.plcoding.snoozeloo.core.domain.db.Alarm
import com.plcoding.snoozeloo.core.domain.db.dao.AlarmsDao
import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity

class UpdateAlarmUseCase(
    private val alarmsDao: AlarmsDao,
    private val mapper: DataMapper<Alarm, AlarmEntity>,
    private val rescheduleAlarmUse: RescheduleAlarmUseCase,
    private val cancelAlarmUseCase: CancelAlarmUseCase
) {
    suspend operator fun invoke(alarmEntity: AlarmEntity) {
        mapper.convert(alarmEntity)?.let { alarm ->
            alarmsDao.upsert(alarm)
            if(alarmEntity.isEnabled) {
                rescheduleAlarmUse.invoke(alarm)
            } else {
                cancelAlarmUseCase.invoke(alarm)
            }
        }
    }
}
