package com.plcoding.snoozeloo.alarm_selection.presentation

import android.net.Uri
import com.plcoding.snoozeloo.alarm_selection.domain.GetSystemRingtonesUseCase
import com.plcoding.snoozeloo.core.domain.entity.RingtoneEntity

class RingtonesManager(private val getSystemRingtonesUseCase: GetSystemRingtonesUseCase) {

    suspend fun getAllRingtones(): List<RingtoneEntity> {
        return getSystemRingtonesUseCase()
    }

    suspend fun getRingtoneByUri(uriPath: String): RingtoneEntity {
        val ringtones = getSystemRingtonesUseCase()
        val index = ringtones.indexOfFirst { it.uri.toString() == uriPath }
        return when (index) {
            -1 -> RingtoneEntity.asMute()
            0 -> RingtoneEntity.asDefault(ringtones.first())
            else -> ringtones[index]
        }
    }

    suspend fun getDefaultRingtone(): RingtoneEntity {
        val ringtones = getSystemRingtonesUseCase()
        return RingtoneEntity.asDefault(ringtones.first())
    }

}
