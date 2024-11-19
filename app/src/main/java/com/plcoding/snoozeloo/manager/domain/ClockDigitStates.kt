package com.plcoding.snoozeloo.manager.domain

data class ClockDigitStates(
    val currentSelectionState: FocusedSelection = FocusedSelection.INACTIVE,
    val allStates: MutableMap<FocusedSelection, DigitFieldData> = mutableMapOf(
        FocusedSelection.HOURS_1 to DigitFieldData(),
        FocusedSelection.HOURS_2 to DigitFieldData(),
        FocusedSelection.MINUTES_1 to DigitFieldData(),
        FocusedSelection.MINUTES_2 to DigitFieldData()
    ),
    private val digitsHolder: MutableMap<FocusedSelection, Int> = mutableMapOf(
        FocusedSelection.HOURS_1 to 0,
        FocusedSelection.HOURS_2 to 0,
        FocusedSelection.MINUTES_1 to 0,
        FocusedSelection.MINUTES_2 to 0,
    )
) {

    fun asNewAlarm(): ClockDigitStates = copy(
        currentSelectionState = FocusedSelection.INACTIVE,
        allStates = allStates.forAllStates { it.reset() })

    fun toAcceptedState(): ClockDigitStates =
        copy(currentSelectionState = FocusedSelection.COMPLETED,
            allStates = allStates.forAllStates { it.accept() })

    fun toInactiveState(): ClockDigitStates =
        copy(currentSelectionState = FocusedSelection.INACTIVE,
            allStates = allStates.forAllStates { it.makeInactive() })

    fun toCorrectedState(): ClockDigitStates {
        val fixedHours = fixHours(digitsHolder)
        var finalStates = allStates
        finalStates = finalStates.mapValues { entry ->
            val selection = entry.key
            val digit = fixedHours[entry.key] ?: 0
            val digitFieldData = finalStates[selection] ?: DigitFieldData()
            digitFieldData.copy(value = digit.toString(), state = DigitFieldState.IS_SET)
        }.toMutableMap()
        return copy(allStates = finalStates)
    }

    fun startMinutesEdit(): ClockDigitStates {
        return copy(
            currentSelectionState = FocusedSelection.MINUTES_1,
            allStates = allStates.mapValues { (selection, fieldData) ->
                if (selection == FocusedSelection.MINUTES_1) fieldData.edit()
                else if (fieldData.isEditing()) fieldData.accept()
                else fieldData
            }.toMutableMap()
        )
    }

    fun startHoursEdit(): ClockDigitStates {
        return copy(
            currentSelectionState = FocusedSelection.HOURS_1,
            allStates = allStates.mapValues { (selection, fieldData) ->
                if (selection == FocusedSelection.HOURS_1) fieldData.edit()
                else if (fieldData.isEditing()) fieldData.accept()
                else fieldData
            }.toMutableMap()
        )
    }

    fun isEditModeEnabled(): Boolean =
        currentSelectionState != FocusedSelection.INACTIVE && currentSelectionState != FocusedSelection.COMPLETED

    fun setNewDigit(digit: String): ClockDigitStates {
        // digit int validation
        val userEnteredDigit = digit.toIntOrNull() ?: return this

        // new state after above digit has been entered to current selection ( if any )
        val newSelectionState = when (currentSelectionState) {
            FocusedSelection.HOURS_1 -> FocusedSelection.HOURS_2
            FocusedSelection.HOURS_2 -> FocusedSelection.MINUTES_1
            FocusedSelection.MINUTES_1 -> FocusedSelection.MINUTES_2
            FocusedSelection.MINUTES_2 -> FocusedSelection.COMPLETED
            FocusedSelection.COMPLETED -> FocusedSelection.COMPLETED
            FocusedSelection.INACTIVE -> FocusedSelection.INACTIVE
        }

        // set new value into currently edited digit ( with validation for this digit only )
        var digitsHolder = digitsHolder
        digitsHolder = modifyDigitsHolder(userEnteredDigit, digitsHolder, currentSelectionState)

        // set improper values to it's defaults
        digitsHolder = fixHours(digitsHolder)

        var finalStates = allStates
        finalStates = finalStates.forState(currentSelectionState) {
            val currentSelectionValue: String =
                getNewValueForState(digitsHolder, currentSelectionState)
            it.copy(value = currentSelectionValue, state = DigitFieldState.IS_SET)
        }

        val newDigitSelected = currentSelectionState != newSelectionState
        // make another digit ready for input
        if (newDigitSelected && newSelectionState != FocusedSelection.INACTIVE) {
            val newSelectionValue: String = getNewValueForState(digitsHolder, newSelectionState)
            finalStates = finalStates.forState(newSelectionState) { selection ->
                selection.copy(
                    value = newSelectionValue,
                    state = DigitFieldState.IS_WAITING_FOR_INPUT
                )
            }
        }
        return copy(allStates = finalStates, currentSelectionState = newSelectionState)
    }

    private fun modifyDigitsHolder(
        userEnteredDigit: Int,
        digitsHolder: MutableMap<FocusedSelection, Int>,
        focusedSelection: FocusedSelection
    ): MutableMap<FocusedSelection, Int> {
        val currentValue = digitsHolder[focusedSelection] ?: 0
        val newValue = when (focusedSelection) {
            FocusedSelection.HOURS_1 -> if (userEnteredDigit <= 2) userEnteredDigit else currentValue
            FocusedSelection.HOURS_2 -> userEnteredDigit
            FocusedSelection.MINUTES_1 -> if (userEnteredDigit <= 5) userEnteredDigit else currentValue
            FocusedSelection.MINUTES_2 -> userEnteredDigit
            FocusedSelection.INACTIVE -> currentValue
            FocusedSelection.COMPLETED -> currentValue
        }
        digitsHolder[focusedSelection] = newValue
        return digitsHolder
    }

    private fun fixHours(digitsHolder: MutableMap<FocusedSelection, Int>): MutableMap<FocusedSelection, Int> {
        val firstHourDigit = digitsHolder[FocusedSelection.HOURS_1] ?: 0
        val secondHourDigit = digitsHolder[FocusedSelection.HOURS_2] ?: 0
        if (firstHourDigit == 2 && secondHourDigit >= 4) {
            digitsHolder[FocusedSelection.HOURS_2] = 0
        }
        return digitsHolder
    }

    private fun getNewValueForState(
        digitsHolder: MutableMap<FocusedSelection, Int>,
        currentSelection: FocusedSelection
    ): String = (digitsHolder[currentSelection] ?: 0).toString()

}
