package com.plcoding.snoozeloo.core.domain.entity

import android.net.Uri

data class RingtoneEntity(val uri: Uri, val title: String) {
    companion object {
        fun asDefault(): RingtoneEntity {
            return RingtoneEntity(Uri.parse(""), "Default")
        }
    }
}
