package com.plcoding.snoozeloo.alarm_selection.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.plcoding.snoozeloo.R

@Composable
fun AlarmIcon(modifier: Modifier = Modifier) {
    Box(
        modifier.then(
            Modifier
                .size(30.dp)
                .background(color = MaterialTheme.colorScheme.background, shape = CircleShape).padding(5.dp)
        )
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.icon_bell),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}