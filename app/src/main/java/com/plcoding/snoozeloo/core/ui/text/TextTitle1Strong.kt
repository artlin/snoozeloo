package com.plcoding.snoozeloo.core.ui.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.plcoding.snoozeloo.core.ui.theme.LocalTextStyleTokens

@Composable
fun TextTitle1Strong(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        textAlign = textAlign,
        text = text,
        color = color,
        style = LocalTextStyleTokens.current.title1Strong
    )
}