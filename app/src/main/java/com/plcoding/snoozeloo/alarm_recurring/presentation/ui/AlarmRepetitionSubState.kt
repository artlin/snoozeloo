package com.plcoding.snoozeloo.alarm_recurring.presentation.ui

data class AlarmRepetitionSubState(val selected: List<Boolean>) {
    fun toggleDay(atIndex: Int): AlarmRepetitionSubState {
        // Toggle the value at the specified index
        val updatedList = selected.mapIndexed { index, value ->
            if (index == atIndex) !value else value
        }
        return copy(selected = updatedList)
    }

    companion object {
        fun getDefault(): AlarmRepetitionSubState =
            AlarmRepetitionSubState(selected = List(7) { true })
    }
}
