package com.plcoding.snoozeloo.alarm_selection.presentation

import com.plcoding.snoozeloo.alarm_selection.domain.GetSystemRingtonesUseCase
import com.plcoding.snoozeloo.core.domain.entity.RingtoneEntity

class RingtonesManager(private val getSystemRingtonesUseCase: GetSystemRingtonesUseCase) {

    suspend fun getAllRingtones(): List<RingtoneEntity> {
        return getSystemRingtonesUseCase()
    }

}
