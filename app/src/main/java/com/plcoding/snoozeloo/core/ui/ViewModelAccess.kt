package com.plcoding.snoozeloo.core.ui

import androidx.compose.runtime.State

interface ViewModelAccess<StateClass, UIEvent> {
    // exposes view state/states
    val state: State<StateClass>

    // handle events from composables
    fun onEvent(event: UIEvent)
}