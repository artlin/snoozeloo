package com.plcoding.snoozeloo.core.domain

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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
import android.view.WindowManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class LockScreenAlarmActivity : ComponentActivity() {

    private val closeAppReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            finishAndRemoveTask()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        LocalBroadcastManager.getInstance(this).registerReceiver(
            closeAppReceiver,
            IntentFilter("ACTION_CLOSE_APP")
        )

        // Add these window flags to ensure the activity shows above lock screen
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
        )

        // For newer Android versions
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
            val keyguardManager = getSystemService(android.app.KeyguardManager::class.java)
            keyguardManager?.requestDismissKeyguard(this, null)
        }

        setContent {
            SnoozelooTheme {
                val navController = koinInject<NavigationController>()
                val alarmId = intent.getIntExtra("ALARM_ID", -1)
                val viewModel: LockScreenAlarmViewModel = koinViewModel()
                val state = viewModel.uiState.value

                if (state.shouldClose) {
                    finishAndRemoveTask() // Use this instead of finishAffinity()
                }

                println("AlarmId: $alarmId")
                viewModel.onEvent(LockScreenAlarmEvent.AlarmSet(alarmId))

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    innerPadding
                    AlarmNotificationComponent(
                        state = viewModel.uiState.value,
                        onLockScreenAlarm = viewModel::onEvent
                    )
                }
            }
        }
    }

//    override fun onBackPressed() {
//        // Optionally override back press to prevent accidental dismissal
//        // You can either do nothing or handle it in a specific way
//    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(closeAppReceiver)
    }

}