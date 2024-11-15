package com.plcoding.snoozeloo.core.ui.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.plcoding.snoozeloo.core.ui.theme.LocalTextStyleTokens

@Composable
fun TextBody(
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
        style = LocalTextStyleTokens.current.body
    )
}

@Composable
fun TextBodyStrong(
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
        style = LocalTextStyleTokens.current.bodyStrong
    )
}

@Composable
fun TextH1(
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
        style = LocalTextStyleTokens.current.h1
    )
}

@Composable
fun TextH2(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        textAlign = textAlign, text = text, color = color, style = LocalTextStyleTokens.current.h2
    )
}

@Composable
fun TextH3(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        textAlign = textAlign, text = text, color = color, style = LocalTextStyleTokens.current.h3
    )
}

@Composable
fun TextLabel(
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
        style = LocalTextStyleTokens.current.label
    )
}

@Composable
fun TextTitle1(
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
        style = LocalTextStyleTokens.current.title1
    )
}

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

@Composable
fun TextTitle2(
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
        style = LocalTextStyleTokens.current.title2
    )
}

@Composable
fun TextTitle2Strong(
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
        style = LocalTextStyleTokens.current.title2Strong
    )
}