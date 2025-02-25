package com.plcoding.snoozeloo.navigation.custom

import SerializableNavType
import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity
import com.plcoding.snoozeloo.core.domain.value.RingtoneId
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

object CustomNavType {

    val RingtoneIdNavType = SerializableNavType<RingtoneId>(
        type = typeOf<RingtoneId>(),
        isNullableAllowed = false
    )

    val AlarmMetadataNavType = SerializableNavType<AlarmEntity?>(
        type = typeOf<AlarmEntity?>(),
        isNullableAllowed = true
    )


//    val AlarmMetadataNavType = object :
//        NavType<AlarmEntity?>(isNullableAllowed = true) { // Changed to non-nullable AlarmEntity type
//        override fun get(bundle: Bundle, key: String): AlarmEntity? {
//            return Json.decodeFromString(
//                bundle.getString(key) ?: throw IllegalArgumentException("Null not allowed")
//            )
//        }
//
//        override fun parseValue(value: String): AlarmEntity? {
//            return Json.decodeFromString(Uri.decode(value))
//        }
//
//        override fun serializeAsValue(value: AlarmEntity?): String {
//            return Uri.encode(Json.encodeToString(value))
//        }
//
//        override fun put(bundle: Bundle, key: String, value: AlarmEntity?) {
//            bundle.putString(key, Json.encodeToString(value))
//        }
//    }
}