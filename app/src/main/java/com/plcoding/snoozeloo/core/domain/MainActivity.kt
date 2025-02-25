package com.plcoding.snoozeloo.core.domain

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.plcoding.snoozeloo.core.data.mapper.DataMapper
import com.plcoding.snoozeloo.core.domain.db.Alarm
import com.plcoding.snoozeloo.core.domain.db.dao.AlarmsDao
import com.plcoding.snoozeloo.core.ui.theme.SnoozelooTheme
import com.plcoding.snoozeloo.core.domain.entity.AlarmEntity
import com.plcoding.snoozeloo.scheduler.AlarmSchedulerImpl
import com.plcoding.snoozeloo.navigation.NavigationController
import com.plcoding.snoozeloo.navigation.NavigationControllerImpl
import com.plcoding.snoozeloo.navigation.graph.RootGraph
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w("TAG", "MainActivity onCreate")
        enableEdgeToEdge()
        val permissions = mutableListOf<String>()
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                permissions.add(Manifest.permission.POST_NOTIFICATIONS)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                permissions.add(Manifest.permission.SCHEDULE_EXACT_ALARM)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> {
                permissions.add(Manifest.permission.FOREGROUND_SERVICE)
            }
        }

        ActivityCompat.requestPermissions(
            this,
            permissions.toTypedArray(),
            0
        )

        setContent {
            KoinContext {
                SnoozelooTheme {
                    val viewModel: LockScreenAlarmViewModel = koinViewModel()
                    val navController = koinInject<NavigationController>()

                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        // navigation
                        val navHostController = rememberNavController()
                        (navController as? NavigationControllerImpl)?.setNavController(
                            navHostController
                        )
                        RootGraph(navController = navHostController, innerPadding)
                    }
                }
            }
        }
    }

    @Composable
    private fun TestCase(){
        val alarmsDao = koinInject<AlarmsDao>()
        val alarms by alarmsDao.getAll().collectAsState(initial = emptyList())
        val scope = rememberCoroutineScope()

        val alarmEntityMapper = koinInject<DataMapper<AlarmEntity, Alarm>>()

        val alarmSchedulerImpl = koinInject<AlarmSchedulerImpl>()
    }
}
