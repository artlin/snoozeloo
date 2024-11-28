package com.plcoding.snoozeloo.core.domain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.plcoding.snoozeloo.alarm_notification.ui.AlarmNotificationComponent
import com.plcoding.snoozeloo.core.ui.theme.SnoozelooTheme
import com.plcoding.snoozeloo.navigation.NavigationController
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

class LockScreenAlarmActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            SnoozelooTheme {
                val viewModel: MainViewModel = koinViewModel()
                val navController = koinInject<NavigationController>()


                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    innerPadding
                    AlarmNotificationComponent()
                }
            }
        }

    }


}