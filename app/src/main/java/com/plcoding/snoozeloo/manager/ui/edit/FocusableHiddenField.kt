package com.plcoding.snoozeloo.manager.ui.edit

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun FocusableHiddenField(focusRequester: FocusRequester, onUserEnteredValue: OnClickWithStringValue) {
    TextField(
        value = "",
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        modifier = Modifier
            .alpha(0f)
            .focusRequester(focusRequester)
            .onKeyEvent { keyEvent ->
                if (keyEvent.nativeKeyEvent.action == NativeKeyEvent.ACTION_UP) {
                    // Capture digit keys
                    val digit = keyEvent.nativeKeyEvent.unicodeChar.toChar()
                    if (digit.isDigit()) {
                        onUserEnteredValue(digit.toString())
                    }
                }
                false // Return false to allow further processing of the key event if needed
            },
        onValueChange = {

        },
    )
}