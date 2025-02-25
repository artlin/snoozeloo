package com.plcoding.snoozeloo.core.data.mapper

import com.plcoding.snoozeloo.core.domain.db.Alarm
import com.plcoding.snoozeloo.core.domain.db.converters.Converters
import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity
import com.plcoding.snoozeloo.core.domain.entity.ClockTime
import com.plcoding.snoozeloo.core.domain.value.AlarmName

class AlarmEntityMapper : DataMapper<Alarm, AlarmEntity>() {

    override suspend fun fromAtoB(input: Alarm): AlarmEntity = AlarmEntity(
        uid = input.id,
        alarmName = AlarmName(input.name),
        isEnabled = input.isActive,
        ringtoneId = input.alarmRingtoneId,
        isVibrateEnabled = input.shouldVibrate,
        volume = input.volume,
        clockTime = ClockTime(input.hours, input.minutes),
        days = Converters().toBooleanList(input.isEnabledAtWeekDay)
    )

    override suspend fun fromBtoA(input: AlarmEntity): Alarm = Alarm(
        id = input.uid,
        period = "Once",
        name = input.alarmName.value,
        isActive = input.isEnabled,
        alarmRingtoneId = input.ringtoneId,
        shouldVibrate = input.isVibrateEnabled,
        volume = input.volume,
        minutes = input.clockTime.minutes,
        hours = input.clockTime.hours,
        isEnabledAtWeekDay = Converters().fromBooleanList(input.days)
    )
}
