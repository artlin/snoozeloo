package com.plcoding.snoozeloo.manager.domain

import com.plcoding.snoozeloo.core.data.mapper.DataMapper
import com.plcoding.snoozeloo.core.domain.db.Alarm
import com.plcoding.snoozeloo.core.domain.db.dao.AlarmsDao
import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity

class UpdateAlarmUseCase(
    private val alarmsDao: AlarmsDao,
    private val mapper: DataMapper<AlarmEntity, Alarm>
) {
    suspend operator fun invoke(alarmEntity: AlarmEntity) {
        mapper.convert(alarmEntity)?.let { alarm ->
            alarmsDao.upsert(alarm)
        }
    }
}
