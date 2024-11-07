package com.plcoding.snoozeloo.core.ui.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.plcoding.snoozeloo.core.ui.theme.LocalTextStyleTokens

@Composable
fun TextH3(text: String, color: Color) {
    Text(text = text, color = color, style = LocalTextStyleTokens.current.h3)
}