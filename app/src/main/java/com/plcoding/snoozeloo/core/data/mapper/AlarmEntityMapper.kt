package com.plcoding.snoozeloo.core.data.mapper

import com.plcoding.snoozeloo.core.domain.db.Alarm
import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity
import com.plcoding.snoozeloo.core.domain.value.TimeValue

class AlarmEntityMapper : DataMapper<Alarm, AlarmEntity>() {

    override suspend fun fromAtoB(input: Alarm): AlarmEntity? = AlarmEntity(
        uid = input.id.toString(),
        alarmName = input.name,
        isEnabled = input.isActive,
        alarmTime = TimeValue(input.startTime * 1000),
        ringtoneId = input.alarmRingtoneId,
        isVibrateEnabled = input.shouldVibrate,
        volume = input.volume,
        minutes = input.minutes,
        hours = input.hours
    )

    override suspend fun fromBtoA(input: AlarmEntity): Alarm = Alarm(
        id = input.uid.toInt(),
        startTime = input.alarmTime.value,
        period = "Once",
        name = input.alarmName,
        isActive = input.isEnabled,
        alarmRingtoneId = input.ringtoneId,
        shouldVibrate = input.isVibrateEnabled,
        volume = input.volume,
        minutes = input.minutes,
        hours = input.hours
    )
}
