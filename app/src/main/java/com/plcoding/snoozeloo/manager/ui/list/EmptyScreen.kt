package com.plcoding.snoozeloo.manager.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.plcoding.snoozeloo.R
import com.plcoding.snoozeloo.core.ui.text.TextTitle2

@Composable
fun EmptyScreen() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(35.dp, alignment = Alignment.CenterVertically)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_alarm),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        TextTitle2(
            text = "It's empty! Add the first alarm so you don't miss an important moment!",
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
    }
}