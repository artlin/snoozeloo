package com.plcoding.snoozeloo.manager.ui.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.plcoding.snoozeloo.core.domain.value.AlarmName
import com.plcoding.snoozeloo.core.ui.headerbuttons.HeaderButtonType
import com.plcoding.snoozeloo.core.ui.headerbuttons.SingleButtonState
import com.plcoding.snoozeloo.core.ui.text.TextTitle2Strong

@Composable
fun EditAlarmNameDialog(text: String, onEditAlarm: OnEditAlarm) {

    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = text,
                selection = TextRange(text.length)
            )
        )
    }
    // Update the text field value when text prop changes, but preserve cursor position
    LaunchedEffect(text) {
        if (text != textFieldValue.text) {
            textFieldValue = textFieldValue.copy(text = text)
        }
    }

    Dialog(onDismissRequest = { onEditAlarm(EditAlarmEvent.OnAlarmNameDismiss) }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 8.dp
        ) {
            Column(
                Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                TextTitle2Strong(text = "Alarm Name", color = MaterialTheme.colorScheme.onSurface)
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFFF0F4FA),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    value = textFieldValue,
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color(0xFF3A506B),
                        unfocusedTextColor = Color(0xFF3A506B),
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                        errorContainerColor = Color.Transparent,
                    ),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    onValueChange = { newValue: TextFieldValue ->
                        textFieldValue = newValue
                        onEditAlarm(EditAlarmEvent.OnAlarmNameChanged(AlarmName(textFieldValue.text)))
                    })

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Button(
                        enabled = true,
                        onClick = {
                            onEditAlarm(EditAlarmEvent.SaveAlarmNameClicked)
                        }) {
                        TextTitle2Strong(
                            text = "Save",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }

        }
    }
}