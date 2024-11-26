package com.plcoding.snoozeloo.manager.ui.edit

data class DigitFieldData(
    val value: String = "",
    val state: DigitFieldState = DigitFieldState.INACTIVE,
) {
    fun reset(): DigitFieldData = copy(value = "0", state = DigitFieldState.INACTIVE)

    fun makeInactive(): DigitFieldData = copy(state = DigitFieldState.INACTIVE)

    fun accept(): DigitFieldData = copy(state = DigitFieldState.IS_SET)

    fun edit(): DigitFieldData = copy(state = DigitFieldState.IS_WAITING_FOR_INPUT)

    fun isEditing(): Boolean = state == DigitFieldState.IS_WAITING_FOR_INPUT

  companion object{
      fun setValueOrEmpty(value: Int): DigitFieldData =
          DigitFieldData(value.toString(), DigitFieldState.IS_SET)
  }
}
