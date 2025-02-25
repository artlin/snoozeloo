package com.plcoding.snoozeloo.manager.ui.edit

import android.util.Log
import android.view.ViewTreeObserver
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.plcoding.snoozeloo.core.ui.text.TextH2
import com.plcoding.snoozeloo.core.ui.theme.SnoozelooTheme


@Composable
fun TimeComponent(
    state: ClockDigitStates,
    onComponentClick: OnComponentClick,
    onUserEnteredValue: OnClickWithStringValue,
    onKeyboardHidden: OnClick
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val view = LocalView.current
    val viewTreeObserver = view.viewTreeObserver
    DisposableEffect(viewTreeObserver) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val isKeyboardOpen =
                ViewCompat.getRootWindowInsets(view)?.isVisible(WindowInsetsCompat.Type.ime())
                    ?: true
            if (isKeyboardOpen.not()) onKeyboardHidden()
        }

        viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }

    LaunchedEffect(state.isEditModeEnabled()) {
        if (state.isEditModeEnabled()) {
            Log.d("TimeComponent", "show keyboard and request focus")
            focusRequester.requestFocus()
            keyboardController?.show()
        } else {
            Log.d("TimeComponent", "hide")
            focusRequester.freeFocus()
            keyboardController?.hide()
        }
    }
    Box(contentAlignment = Alignment.Center) {
        FocusableHiddenField(focusRequester) { enteredText ->
            onUserEnteredValue(enteredText)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
        ) {
            val digitModifier = Modifier.wrapContentWidth()
            TimeComponentBg(onClick = { onComponentClick(ComponentClickType.HOURS) }, {
                OneDigitField(digitModifier, state.allStates[FocusedSelection.HOURS_1])
            }, {
                OneDigitField(digitModifier, state.allStates[FocusedSelection.HOURS_2])
            })
            Colon()
            TimeComponentBg(onClick = { onComponentClick(ComponentClickType.MINUTES) }, {
                OneDigitField(digitModifier, state.allStates[FocusedSelection.MINUTES_1])
            }, {
                OneDigitField(digitModifier, state.allStates[FocusedSelection.MINUTES_2])
            })
        }
    }
}

@Composable
fun OneDigitField(modifier: Modifier, digitData: DigitFieldData?) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val blink by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 0.3f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val color by animateColorAsState(
        targetValue = when (digitData?.state) {
            DigitFieldState.INACTIVE -> MaterialTheme.colorScheme.onSurfaceVariant
            DigitFieldState.IS_SET -> MaterialTheme.colorScheme.primary
            DigitFieldState.IS_WAITING_FOR_INPUT -> MaterialTheme.colorScheme.onSurfaceVariant
            null -> MaterialTheme.colorScheme.error
        }, animationSpec = tween(durationMillis = 500), label = ""
    )

    val animatedModifier = when (digitData?.state) {
        DigitFieldState.INACTIVE -> Modifier
        DigitFieldState.IS_SET -> Modifier
        DigitFieldState.IS_WAITING_FOR_INPUT -> Modifier.alpha(blink)
        null -> Modifier
    }

    TextH2(
        modifier = modifier.then(animatedModifier),
        text = digitData?.value ?: "",
        color = color,
        textAlign = TextAlign.Center
    )
}

@Preview(device = Devices.PIXEL_4)
@Composable
fun PreviewOneDigitFields() {
    SnoozelooTheme {
        val modifier = Modifier.wrapContentWidth()
        val clockDigitStates = ClockDigitStates().asNewAlarm()
        OneDigitField(modifier, clockDigitStates.allStates[FocusedSelection.HOURS_1])
    }
}

@Preview(device = Devices.PIXEL_4)
@Composable
fun PreviewTwoDigitFields() {
    SnoozelooTheme {
        val clockDigitStates = ClockDigitStates().asNewAlarm()

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val modifier = Modifier.wrapContentWidth()
            TimeComponentBg(onClick = { }, firstDigit = {
                OneDigitField(modifier, clockDigitStates.allStates[FocusedSelection.HOURS_1])
            }, secondDigit = {
                OneDigitField(modifier, clockDigitStates.allStates[FocusedSelection.HOURS_1])
            })
        }
    }
}

@Preview(device = Devices.PIXEL_4)
@Composable
fun PreviewTimeComponent() {
    SnoozelooTheme {
        val clockDigitStates = ClockDigitStates().asNewAlarm()
        TimeComponent(clockDigitStates, { }, { }, {})
    }
}

typealias OnComponentClick = (ComponentClickType) -> Unit
typealias OnClickWithIntValue = (Int) -> Unit
typealias OnClickWithStringValue = (String) -> Unit
typealias OnClickWithBooleanValue = (Boolean) -> Unit
typealias OnClick = () -> Unit