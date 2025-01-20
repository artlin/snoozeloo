package com.plcoding.snoozeloo.manager.domain

import com.plcoding.snoozeloo.core.data.mapper.DataMapper
import com.plcoding.snoozeloo.core.domain.db.Alarm
import com.plcoding.snoozeloo.core.domain.db.dao.AlarmsDao
import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity

class RemoveAlarmUseCase(
    private val alarmsDao: AlarmsDao,
    private val mapper: DataMapper<Alarm, AlarmEntity>,
    private val cancelAlarmUseCase: CancelAlarmUseCase
) {
    suspend operator fun invoke(alarmEntity: AlarmEntity) {
        val dbAlarm = mapper.convert(alarmEntity) ?: return
        cancelAlarmUseCase(dbAlarm)
        alarmsDao.delete(dbAlarm)
    }
}
