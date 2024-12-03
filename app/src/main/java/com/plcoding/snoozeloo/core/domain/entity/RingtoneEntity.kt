package com.plcoding.snoozeloo.core.domain.entity

import android.net.Uri
import com.plcoding.snoozeloo.core.domain.value.AlarmName

data class RingtoneEntity(
    val uri: Uri,
    val title: AlarmName,
    val specialType: SpecialRingtoneType = SpecialRingtoneType.NONE
) {
    companion object {
        fun asDefault(ringtoneEntity: RingtoneEntity): RingtoneEntity {
            return ringtoneEntity.copy(specialType = SpecialRingtoneType.DEFAULT)
        }

        fun asMute(): RingtoneEntity {
            return RingtoneEntity(
                Uri.parse(""),
                AlarmName(""),
                specialType = SpecialRingtoneType.MUTE
            )
        }

    }
}

enum class SpecialRingtoneType {
    MUTE, DEFAULT, NONE
}