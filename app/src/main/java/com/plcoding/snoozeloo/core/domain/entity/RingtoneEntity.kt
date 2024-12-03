package com.plcoding.snoozeloo.core.domain.entity

import android.net.Uri
import com.plcoding.snoozeloo.core.domain.value.AlarmName

data class RingtoneEntity(val uri: Uri, val title: AlarmName) {
    companion object {
        fun asDefault(): RingtoneEntity {
            return RingtoneEntity(Uri.parse(""), AlarmName("Default"))
        }

    }
}
