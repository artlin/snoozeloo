package com.plcoding.snoozeloo.manager.domain

enum class FocusedSelection {
    INACTIVE, HOURS_1, HOURS_2, MINUTES_1, MINUTES_2, COMPLETED
}

fun MutableMap<FocusedSelection, DigitFieldData>.forAllStates(predicate: (DigitFieldData) -> DigitFieldData): MutableMap<FocusedSelection, DigitFieldData> =
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
