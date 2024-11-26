package com.plcoding.snoozeloo.core.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class ClockTime(val hours: Int, val minutes: Int) {


    companion object {
        fun asEmpty(): ClockTime = ClockTime(0, 0)
    }
}
