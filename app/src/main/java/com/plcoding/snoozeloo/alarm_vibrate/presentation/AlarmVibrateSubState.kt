package com.plcoding.snoozeloo.alarm_vibrate.presentation

data class AlarmVibrateSubState(val isVibrateEnabled: Boolean) {

    fun toggleVibrate(): AlarmVibrateSubState {
        return copy(isVibrateEnabled = isVibrateEnabled.not())
    }

    companion object {
        fun getDefault(): AlarmVibrateSubState {
            return AlarmVibrateSubState(false)
        }
    }
}
