package com.plcoding.snoozeloo.manager.ui.edit

import com.plcoding.snoozeloo.core.domain.value.AlarmName

data class AlarmNameSubState(
    val name: AlarmName,
    val editedName: AlarmName,
    val isDialogShown: Boolean = false
) {

    fun openDialogWithName(name: AlarmName): AlarmNameSubState {
        return AlarmNameSubState(name = name, editedName = name, isDialogShown = true)
    }

    fun saveNameIfNotEmpty(): AlarmNameSubState {
        val editedNameIsNotEmpty = editedName.value.isNotEmpty()
        return if (editedNameIsNotEmpty) {
            copy(name = editedName, isDialogShown = false)
        } else {
            this
        }
    }

    fun dismissDialogResetValues(): AlarmNameSubState {
        return copy(editedName = name, isDialogShown = false)
    }

    fun updateName(newName: AlarmName): AlarmNameSubState {
        return copy(editedName = newName)
    }

    companion object {
        fun asDefault() =
            AlarmNameSubState(name = AlarmName("Default"), editedName = AlarmName("Default"))

        fun fromName(alarmName: AlarmName) =
            AlarmNameSubState(name = alarmName, editedName = alarmName)
    }
}
