package com.plcoding.snoozeloo.core.domain.db.converters

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromBooleanList(list: List<Boolean>): String {
        return list.joinToString(",") { it.toString() }
    }

    @TypeConverter
    fun toBooleanList(data: String): List<Boolean> {
        return data.split(",").map { it.toBoolean() }
    }
}