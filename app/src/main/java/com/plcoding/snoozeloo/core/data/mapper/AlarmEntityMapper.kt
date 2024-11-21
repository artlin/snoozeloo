package com.plcoding.snoozeloo.core.data.mapper

import com.plcoding.snoozeloo.core.domain.db.Alarm
import com.plcoding.snoozeloo.manager.domain.AlarmEntity
import com.plcoding.snoozeloo.manager.domain.TimeValue

class AlarmEntityMapper : DataMapper<AlarmEntity, Alarm>() {

    override suspend fun fromAtoB(input: AlarmEntity): Alarm? {
        return Alarm(
            id = input.uid.toInt(),
            startTime = input.alarmTime.value,
            period = "Once",
            name = input.alarmName,
            isActive = input.isEnabled,
            alarmRingtoneId = input.ringtoneId,
            shouldVibrate = input.isVibrateEnabled,
            volume = input.volume
        )
    }

    override suspend fun fromBtoA(input: Alarm): AlarmEntity = AlarmEntity(
        uid = input.id.toString(),
        alarmName = input.name,
        isEnabled = input.isActive,
        alarmTime = TimeValue(input.startTime),
        ringtoneId = input.alarmRingtoneId,
        isVibrateEnabled = input.shouldVibrate,
        volume = input.volume
    )

}