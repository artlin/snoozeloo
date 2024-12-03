package com.plcoding.snoozeloo.alarm_selection.domain

import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import com.plcoding.snoozeloo.core.domain.entity.RingtoneEntity
import com.plcoding.snoozeloo.core.domain.value.AlarmName

class GetSystemRingtonesUseCase(private val context: Context) {
    suspend operator fun invoke(): MutableList<RingtoneEntity> {
        val ringtoneManager = RingtoneManager(context)
        ringtoneManager.setType(RingtoneManager.TYPE_ALL)
        val cursor = ringtoneManager.cursor

        val ringtones = mutableListOf<RingtoneEntity>()

        while (cursor.moveToNext()) {
            val title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX).let { originalTitle ->
                if (originalTitle.contains("/")) originalTitle.split("/").last()
                else originalTitle
            }
            val uri = Uri.parse(
                cursor.getString(RingtoneManager.URI_COLUMN_INDEX) + "/" +
                        cursor.getString(RingtoneManager.ID_COLUMN_INDEX)
            )
            ringtones.add(RingtoneEntity(uri, AlarmName(title)))
        }
//content://media/internal/audio/media/257
        return ringtones
    }
}
