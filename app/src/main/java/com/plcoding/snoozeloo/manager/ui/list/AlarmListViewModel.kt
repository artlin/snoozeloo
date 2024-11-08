package com.plcoding.snoozeloo.manager.ui.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.plcoding.snoozeloo.core.ui.ViewModelAccess

class AlarmListViewModel : ViewModel(), ViewModelAccess<AlarmListState, AlarmListEvent> {

    override var state: MutableState<AlarmListState> = mutableStateOf(AlarmListState())

    init {
        // for testing purposes only
        state.value = state.value.copy(
            list = emptyList()
        )
    }

    override fun onEvent(event: AlarmListEvent) {
        when (event) {
            AlarmListEvent.AddAlarmClicked -> {
                // for testing purposes only
                state.value = state.value.copy(
                    list = listOf("d", "s")
                )
            }
        }
    }

}
