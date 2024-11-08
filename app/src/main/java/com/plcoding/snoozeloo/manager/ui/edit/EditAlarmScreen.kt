package com.plcoding.snoozeloo.manager.ui.edit

import androidx.compose.runtime.Composable
import com.plcoding.snoozeloo.core.domain.value.HeaderButtonLabel
import com.plcoding.snoozeloo.core.ui.headerbuttons.ButtonsState
import com.plcoding.snoozeloo.core.ui.headerbuttons.HeaderButtonType
import com.plcoding.snoozeloo.core.ui.headerbuttons.HeaderButtons
import com.plcoding.snoozeloo.core.ui.headerbuttons.SingleButtonState

@Composable
fun EditAlarmScreen() {
    val buttonsState = ButtonsState(
        leftButton = SingleButtonState(
            buttonType = HeaderButtonType.CLOSE,
            enabled = true
        ), rightButton = SingleButtonState(
            buttonType = HeaderButtonType.SAVE,
            label = HeaderButtonLabel("Save")
        )
    )
    HeaderButtons(buttonsState) { }
}