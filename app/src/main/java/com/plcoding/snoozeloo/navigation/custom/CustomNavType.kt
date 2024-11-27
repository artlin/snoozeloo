package com.plcoding.snoozeloo.navigation.custom

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity
import com.plcoding.snoozeloo.core.domain.value.RingtoneId
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavType {

    val RingtoneIdNavType = object : NavType<RingtoneId>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): RingtoneId {
            return RingtoneId(bundle.getInt(key))
        }

        // this one is important as it is extracting proper value for nav pathŌ
        override fun serializeAsValue(value: RingtoneId): String {
            return value.value.toString()
        }

        override fun parseValue(value: String): RingtoneId {
            return RingtoneId(value.toInt())
        }

        override fun put(bundle: Bundle, key: String, value: RingtoneId) {
            bundle.putInt(key, value.value)
        }
    }

    val AlarmMetadataNavType = object : NavType<AlarmEntity?>(isNullableAllowed = true) { // Changed to non-nullable AlarmEntity type
        override fun get(bundle: Bundle, key: String): AlarmEntity? {
            return Json.decodeFromString(bundle.getString(key) ?: throw IllegalArgumentException("Null not allowed"))
        }

        override fun parseValue(value: String): AlarmEntity? {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: AlarmEntity?): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: AlarmEntity?) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
}