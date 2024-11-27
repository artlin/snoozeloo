package com.plcoding.snoozeloo.alarm_selection.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.snoozeloo.alarm_selection.domain.GetSystemRingtonesUseCase
import com.plcoding.snoozeloo.core.domain.value.RingtoneId
import com.plcoding.snoozeloo.core.ui.ViewModelAccess
import com.plcoding.snoozeloo.navigation.NavigationController
import kotlinx.coroutines.launch

class RingtoneViewModel(
    private val navController: NavigationController,
    private val getSystemRingtonesUseCase: GetSystemRingtonesUseCase,
    private val currentRingtone: RingtoneId
) : ViewModel(),
    ViewModelAccess<RingtoneState, RingtoneEvent> {

    override var state: MutableState<RingtoneState> = mutableStateOf(RingtoneState())


    init {
        viewModelScope.launch {
            state.value = state.value.copy(ringtones = getSystemRingtonesUseCase())
        }
    }

    override fun onEvent(event: RingtoneEvent) {
        when (event) {
            is RingtoneEvent.RingtoneSelected -> {

                // Get the actual NavController
                val actualNavController = navController.getCurrentNavController()
                // Set the value explicitly
                actualNavController?.previousBackStackEntry?.savedStateHandle?.set(
                    SELECTED_RINGTONE_KEY,
                    event.ringtoneId.value.toString()
                )
                // Add debug logging
                println("Debug: Setting ringtone value: ${event.ringtoneId.value}")
                navController.navigateBack()
            }
        }

    }
}

const val SELECTED_RINGTONE_KEY = "selected_ringtone"