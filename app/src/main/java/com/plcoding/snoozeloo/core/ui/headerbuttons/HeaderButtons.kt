package com.plcoding.snoozeloo.core.ui.headerbuttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.plcoding.snoozeloo.R
import com.plcoding.snoozeloo.core.domain.value.HeaderButtonLabel
import com.plcoding.snoozeloo.core.ui.text.TextTitle2Strong

@Composable
fun HeaderButtons(buttonsState: ButtonsState, onEvent: HeaderButtonsEvent) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (buttonsState.leftButton.buttonType != HeaderButtonType.NO_BUTTON) {
            HeaderButton(buttonsState.leftButton, onEvent)
        }
        Spacer(Modifier.weight(1f))
        if (buttonsState.rightButton.buttonType != HeaderButtonType.NO_BUTTON) {
            HeaderButton(buttonsState.rightButton, onEvent)
        }
    }
}

@Composable
private fun HeaderButton(buttonState: SingleButtonState, onEvent: HeaderButtonsEvent) {
    when (buttonState.buttonType) {
        HeaderButtonType.CLOSE -> {
            Icon(
                modifier = Modifier.clickable { onEvent(SingleButtonState(buttonType = HeaderButtonType.CLOSE)) },
                painter = painterResource(id = R.drawable.icon_close),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }

        HeaderButtonType.SAVE -> {
            Button(
                enabled = buttonState.enabled,
                onClick = { onEvent(SingleButtonState(buttonType = HeaderButtonType.SAVE)) }) {
                TextTitle2Strong(
                    text = buttonState.label.value,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        HeaderButtonType.BACK_ARROW -> {
            Image(
                modifier = Modifier.clickable { onEvent(SingleButtonState(buttonType = HeaderButtonType.BACK_ARROW)) },
                painter = painterResource(id = R.drawable.icon_back),
                contentDescription = null,
            )
        }

        HeaderButtonType.NO_BUTTON -> Unit
    }
}

data class ButtonsState(
    val leftButton: SingleButtonState,
    val rightButton: SingleButtonState,
)

data class SingleButtonState(
    val buttonType: HeaderButtonType = HeaderButtonType.NO_BUTTON,
    val enabled: Boolean = false,
    val label: HeaderButtonLabel = HeaderButtonLabel("")
)

enum class HeaderButtonType {
    NO_BUTTON, CLOSE, SAVE, BACK_ARROW
}

typealias HeaderButtonsEvent = (SingleButtonState) -> Unit