package com.plcoding.snoozeloo.core.domain

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.plcoding.snoozeloo.core.ui.theme.SnoozelooTheme
import com.plcoding.snoozeloo.navigation.NavigationController
import com.plcoding.snoozeloo.navigation.NavigationControllerImpl
import com.plcoding.snoozeloo.navigation.graph.RootGraph
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w("TAG", "MainActivity onCreate")

        setContent {
            SnoozelooTheme {
                val viewModel: MainViewModel = koinViewModel()
                val navController = koinInject<NavigationController>()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    innerPadding
                    viewModel.doSomething()
                    Log.w("TAG", "MainActivity doSomething")
                    // navigation
                    val navHostController = rememberNavController()
                    DisposableEffect(navHostController) {
                        (navController as? NavigationControllerImpl)?.setNavController(
                            navHostController
                        )
                        onDispose {
                        }
                    }
                    RootGraph(navController = navHostController)
                }
            }
        }
    }
}