package com.plcoding.snoozeloo.navigation.custom

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.plcoding.snoozeloo.core.domain.entity.AlarmMetadata
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavType {
    val AlarmMetadataNavType = object : NavType<AlarmMetadata>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): AlarmMetadata? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): AlarmMetadata {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: AlarmMetadata): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: AlarmMetadata) {
            bundle.putString(key, Json.encodeToString(value))
        }

    }
}