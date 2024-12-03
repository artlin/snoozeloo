package com.plcoding.snoozeloo.alarm_selection.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.snoozeloo.core.domain.entity.RingtoneEntity
import com.plcoding.snoozeloo.core.domain.value.RingtoneId
import com.plcoding.snoozeloo.core.ui.ViewModelAccess
import com.plcoding.snoozeloo.navigation.NavigationController
import kotlinx.coroutines.launch

class RingtoneViewModel(
    private val navController: NavigationController,
    private val ringtonesManager: RingtonesManager,
    private val currentRingtone: RingtoneId
) : ViewModel(),
    ViewModelAccess<RingtoneState, RingtoneEvent> {

    override var uiState: MutableState<RingtoneState> =
        mutableStateOf(RingtoneState(emptyList(), selectedRingtone = RingtoneEntity.asMute()))

    private var newState: RingtoneState =
        RingtoneState(emptyList(), selectedRingtone = RingtoneEntity.asMute())
        set(value) {
            uiState.value = value
        }

    init {
        viewModelScope.launch {
            setRingtonesWithSpecialOptions(systemRingtones = ringtonesManager.getAllRingtones())
        }
    }

    private fun setRingtonesWithSpecialOptions(systemRingtones: List<RingtoneEntity>) {
        val ringtonesWithOptions = buildList {
            add(RingtoneEntity.asMute())
            add(RingtoneEntity.asDefault(systemRingtones.first()))
            addAll(systemRingtones.drop(1))
        }
        newState = uiState.value.copy(ringtones = ringtonesWithOptions)
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

            RingtoneEvent.BackButtonClicked -> {
                navController.navigateBack()
            }
        }

    }
}

const val SELECTED_RINGTONE_KEY = "selected_ringtone"