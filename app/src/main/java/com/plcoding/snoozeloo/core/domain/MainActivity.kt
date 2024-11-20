package com.plcoding.snoozeloo.core.domain

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.plcoding.snoozeloo.core.domain.db.Alarm
import com.plcoding.snoozeloo.core.domain.db.dao.AlarmsDao
import com.plcoding.snoozeloo.core.ui.theme.SnoozelooTheme
import com.plcoding.snoozeloo.navigation.NavigationController
import com.plcoding.snoozeloo.navigation.NavigationControllerImpl
import com.plcoding.snoozeloo.navigation.graph.RootGraph
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import java.time.Instant

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w("TAG", "MainActivity onCreate")

        setContent {
            SnoozelooTheme {
                val viewModel: MainViewModel = koinViewModel()
                val navController = koinInject<NavigationController>()

                val alarmsDao = koinInject<AlarmsDao>()
                val alarms by alarmsDao.getAll().collectAsState(initial = emptyList())
                val scope = rememberCoroutineScope()

                LaunchedEffect(key1 = true) {

                    alarmsDao.deleteAll()

                    val alarmsList = listOf(
                        Alarm(
                            startTime = Instant.now().epochSecond,
                            period = "THURSDAY, SATURDAY",
                            name = "Alarm 1",
                            isActive = true,
                            alarmRingtoneId = "4",
                            shouldVibrate = true,
                            volume = 0.5f,
                            defaultRingingTime = 5 * 60 * 1000L
                        ),
                        Alarm(
                            startTime = Instant.now().epochSecond,
                            period = "MONDAY, WEDNESDAY, FRIDAY",
                            name = "Alarm 2",
                            isActive = false,
                            alarmRingtoneId = "4",
                            shouldVibrate = true,
                            volume = 0.5f,
                            defaultRingingTime = 5 * 60 * 1000L
                        ),
                        Alarm(
                            startTime = Instant.now().epochSecond,
                            period = "FRIDAY, SUNDAY",
                            name = "Alarm 3",
                            isActive = true,
                            alarmRingtoneId = "4",
                            shouldVibrate = false,
                            volume = 0.5f,
                            defaultRingingTime = 5 * 60 * 1000L
                        )
                    )

                    alarmsList.forEach { alarm ->
                        alarmsDao.upsert(alarm)
                    }

                    println("alarms przed usunięciem: $alarms")

                    alarmsDao.delete(alarms.first())

                    println("alarms po usunięciu: $alarms")
                }

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
}

