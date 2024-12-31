package com.plcoding.snoozeloo.alram_recurring.presentation.ui

data class AlarmRepetitionSubState(val selected: List<Boolean>) {

    companion object {
        fun getDefault(): AlarmRepetitionSubState =
            AlarmRepetitionSubState(selected = List(7) { true })
    }
}
