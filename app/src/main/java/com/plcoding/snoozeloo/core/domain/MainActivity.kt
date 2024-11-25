package com.plcoding.snoozeloo.core.domain

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.plcoding.snoozeloo.core.data.mapper.DataMapper
import com.plcoding.snoozeloo.core.domain.db.Alarm
import com.plcoding.snoozeloo.core.domain.db.dao.AlarmsDao
import com.plcoding.snoozeloo.core.ui.theme.SnoozelooTheme
import com.plcoding.snoozeloo.manager.domain.AlarmEntity
import com.plcoding.snoozeloo.manager.domain.AlarmItem
import com.plcoding.snoozeloo.manager.domain.AlarmSchedulerImpl
import com.plcoding.snoozeloo.navigation.NavigationController
import com.plcoding.snoozeloo.navigation.NavigationControllerImpl
import com.plcoding.snoozeloo.navigation.graph.RootGraph
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import java.time.Instant
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w("TAG", "MainActivity onCreate")

        setContent {
            SnoozelooTheme {
                val viewModel: MainViewModel = koinViewModel()
                val navController = koinInject<NavigationController>()

//                TestCase()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    innerPadding
                    viewModel.doSomething()
                    Log.w("TAG", "MainActivity doSomething")
                    // navigation
                    val navHostController = rememberNavController()
                    (navController as? NavigationControllerImpl)?.setNavController(
                        navHostController
                    )
                    RootGraph(navController = navHostController)
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

        val alarmItem = AlarmItem(
            time = LocalDateTime.now().plusMinutes(1),
            message = "DESPICABLE ME 1 min have passed"
        )

        alarmSchedulerImpl.scheduleAlarm(alarmItem)
    }
}

