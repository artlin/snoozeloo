package com.plcoding.snoozeloo.manager.ui.edit

import android.util.Log

data class EditAlarmState(
    val list: List<String> = emptyList(),
    val timeComponentState: TimeComponentState = TimeComponentState()
) {
    fun toNewAlarm(): EditAlarmState {
        return copy(timeComponentState = timeComponentState.toNewAlarm())
    }

    fun startHoursEditMode(): EditAlarmState {
        return copy(timeComponentState = timeComponentState.startHoursEditMode())
    }

    fun startMinutesEditMode(): EditAlarmState {
        return copy(timeComponentState = timeComponentState.startMinutesEditMode())
    }

    fun setNewDigit(digit: String): EditAlarmState {
        return copy(timeComponentState = timeComponentState.setNewDigit(digit))
    }
}


data class TimeComponentState(
    val currentSelectionState: FocusedSelection = FocusedSelection.NONE,
    val allStates: MutableMap<FocusedSelection, DigitFieldData> = mutableMapOf(
        FocusedSelection.HOURS_1 to DigitFieldData(),
        FocusedSelection.HOURS_2 to DigitFieldData(),
        FocusedSelection.MINUTES_1 to DigitFieldData(),
        FocusedSelection.MINUTES_2 to DigitFieldData()
    ),
    private var hours: TwoPlaceNumber = TwoPlaceNumber(0, 0),
    private var minutes: TwoPlaceNumber = TwoPlaceNumber(0, 0)
) {


    fun toNewAlarm(): TimeComponentState = copy(
        currentSelectionState = FocusedSelection.NONE,
        allStates = allStates.forAllStates { it.reset() })

    private fun MutableMap<FocusedSelection, DigitFieldData>.forAllStates(predicate: (DigitFieldData) -> DigitFieldData): MutableMap<FocusedSelection, DigitFieldData> =
        mapValues { entry ->
            predicate(entry.value)
        }.toMutableMap()


    fun MutableMap<FocusedSelection, DigitFieldData>.forState(
        state: FocusedSelection,
        predicate: (DigitFieldData) -> DigitFieldData
    ): MutableMap<FocusedSelection, DigitFieldData> {
        return mapValues { entry ->
            if (entry.key == state) predicate(entry.value)
            else entry.value
        }.toMutableMap()
    }

    fun startMinutesEditMode(): TimeComponentState {
        return copy(
            currentSelectionState = FocusedSelection.MINUTES_1,
            allStates = allStates.mapValues { (selection, fieldData) ->
                if (selection == FocusedSelection.MINUTES_1) fieldData.enterEditState()
                else if (fieldData.isEditing()) fieldData.enterAcceptedState()
                else fieldData
            }.toMutableMap()
        )
    }

    fun startHoursEditMode(): TimeComponentState {
        return copy(
            currentSelectionState = FocusedSelection.HOURS_1,
            allStates = allStates.mapValues { (selection, fieldData) ->
                if (selection == FocusedSelection.HOURS_1) fieldData.enterEditState()
                else if (fieldData.isEditing()) fieldData.enterAcceptedState()
                else fieldData
            }.toMutableMap()
        )
    }

    fun isEditModeEnabled(): Boolean = currentSelectionState != FocusedSelection.NONE

    fun setNewDigit(digit: String): TimeComponentState {
        Log.d("TimeComponentState", "enteredDigit : $digit")
        val validDigit = digit.toIntOrNull() ?: return this
        currentSelectionState
        hours = modifyHours(currentSelectionState, validDigit)
        minutes = modifyMinutes(currentSelectionState, validDigit)

        val newValue: String = getNewValue(currentSelectionState)

        val newSelection = when (currentSelectionState) {
            FocusedSelection.HOURS_1 -> FocusedSelection.HOURS_2
            FocusedSelection.HOURS_2 -> FocusedSelection.MINUTES_1
            FocusedSelection.MINUTES_1 -> FocusedSelection.MINUTES_2
            FocusedSelection.MINUTES_2 -> FocusedSelection.NONE
            FocusedSelection.NONE -> FocusedSelection.NONE
        }
        val newDigitSelected = currentSelectionState != newSelection

        var finalStates = allStates
        finalStates = finalStates.forState(currentSelectionState) {
            it.copy(value = newValue, state = DigitFieldState.IS_SET)
        }

        if (newDigitSelected && newSelection != FocusedSelection.NONE) {
            finalStates = finalStates.forState(newSelection) {
                it.copy(state = DigitFieldState.IS_WAITING_FOR_INPUT)
            }
        }
        return copy(allStates = finalStates, currentSelectionState = newSelection)
    }

    private fun getNewValue(currentSelection: FocusedSelection): String {
        return when (currentSelection) {
            FocusedSelection.HOURS_1 -> "${hours.firstPlace}"
            FocusedSelection.HOURS_2 -> "${hours.secondPlace}"
            FocusedSelection.MINUTES_1 -> "${minutes.firstPlace}"
            FocusedSelection.MINUTES_2 -> "${minutes.secondPlace}"
            FocusedSelection.NONE -> ""
        }
    }

    private fun modifyHours(currentSelection: FocusedSelection, validDigit: Int): TwoPlaceNumber {
        return when (currentSelection) {
            FocusedSelection.HOURS_1 -> {
                val firstPlace = if (validDigit <= 2) validDigit else hours.firstPlace
                hours.setFirstPlace(firstPlace)
            }

            FocusedSelection.HOURS_2 -> {
                val secondPlace = when (hours.firstPlace) {
                    2 -> if (validDigit <= 4) validDigit else 4
                    else -> validDigit
                }
                hours.setSecondPlace(secondPlace)
            }

            else -> hours
        }
    }

    private fun modifyMinutes(currentSelection: FocusedSelection, validDigit: Int): TwoPlaceNumber {
        return when (currentSelection) {
            FocusedSelection.MINUTES_1 -> {
                val firstPlace = if (validDigit <= 5) validDigit else minutes.firstPlace
                minutes.setFirstPlace(firstPlace)
            }

            FocusedSelection.MINUTES_2 -> minutes.setSecondPlace(validDigit)
            else -> minutes
        }
    }
}

