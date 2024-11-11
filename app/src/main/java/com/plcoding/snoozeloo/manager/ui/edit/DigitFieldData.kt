package com.plcoding.snoozeloo.manager.ui.edit

data class DigitFieldData(
    val value: String = "",
    val state: DigitFieldState = DigitFieldState.INACTIVE,
) {
    fun reset(): DigitFieldData = copy(value = "0", state = DigitFieldState.INACTIVE)

    fun enterEditState(): DigitFieldData = copy(state = DigitFieldState.IS_WAITING_FOR_INPUT)

    fun enterAcceptedState(): DigitFieldData = copy(state = DigitFieldState.IS_SET)

    fun isEditing(): Boolean = state == DigitFieldState.IS_WAITING_FOR_INPUT


}