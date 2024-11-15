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


    private fun MutableMap<FocusedSelection, DigitFieldData>.forState(
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
        val newValidDigit = digit.toIntOrNull() ?: return this
        hours = modifyHours(currentSelectionState, newValidDigit)
        minutes = modifyMinutes(currentSelectionState, newValidDigit)

        val newSelectionState = when (currentSelectionState) {
            FocusedSelection.HOURS_1 -> FocusedSelection.HOURS_2
            FocusedSelection.HOURS_2 -> FocusedSelection.MINUTES_1
            FocusedSelection.MINUTES_1 -> FocusedSelection.MINUTES_2
            FocusedSelection.MINUTES_2 -> FocusedSelection.NONE
            FocusedSelection.NONE -> FocusedSelection.NONE
        }

        val currentSelectionValue: String = getNewValueForState(currentSelectionState)
        val newSelectionValue: String = getNewValueForState(newSelectionState)


        val newDigitSelected = currentSelectionState != newSelectionState

        var finalStates = allStates
        finalStates = finalStates.forState(currentSelectionState) {
            it.copy(value = currentSelectionValue, state = DigitFieldState.IS_SET)
        }

        if (newDigitSelected && newSelectionState != FocusedSelection.NONE) {
            finalStates = finalStates.forState(newSelectionState) { selection ->
                selection.copy(
                    value = newSelectionValue,
                    state = DigitFieldState.IS_WAITING_FOR_INPUT
                )
            }
        }
        return copy(allStates = finalStates, currentSelectionState = newSelectionState)
    }

    private fun getNewValueForState(currentSelection: FocusedSelection): String {
        return when (currentSelection) {
            FocusedSelection.HOURS_1 -> "${hours.firstPlace}"
            FocusedSelection.HOURS_2 -> "${hours.secondPlace}"
            FocusedSelection.MINUTES_1 -> "${minutes.firstPlace}"
            FocusedSelection.MINUTES_2 -> "${minutes.secondPlace}"
            FocusedSelection.NONE -> ""
        }
    }

    private fun modifyHours(
        currentSelection: FocusedSelection,
        newValidDigit: Int
    ): TwoPlaceNumber {
        return when (currentSelection) {
            FocusedSelection.HOURS_1 -> {
                val firstPlace = if (newValidDigit <= 2) newValidDigit else hours.firstPlace
                hours.setFirstPlace(firstPlace).let { newHours ->
                    when (firstPlace) {
                        2 -> newHours.setSecondPlace(4)
                        else -> newHours
                    }
                }
            }

            FocusedSelection.HOURS_2 -> {
                val secondPlace = when (hours.firstPlace) {
                    2 -> if (newValidDigit <= 4) newValidDigit else 4
                    else -> newValidDigit
                }
                hours.setSecondPlace(secondPlace)
            }

            else -> hours
        }
    }

    private fun modifyMinutes(
        currentSelection: FocusedSelection,
        newValidDigit: Int
    ): TwoPlaceNumber {
        return when (currentSelection) {
            FocusedSelection.MINUTES_1 -> {
                val firstPlace = if (newValidDigit <= 5) newValidDigit else minutes.firstPlace
                minutes.setFirstPlace(firstPlace)
            }

            FocusedSelection.MINUTES_2 -> minutes.setSecondPlace(newValidDigit)
            else -> minutes
        }
    }
}

