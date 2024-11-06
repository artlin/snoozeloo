package com.plcoding.snoozeloo.core.domain

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.plcoding.snoozeloo.core.domain.navigation.RootGraph
import com.plcoding.snoozeloo.core.ui.theme.SnoozelooTheme
import org.koin.compose.viewmodel.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.w("TAG", "MainActivity onCreate")

        setContent {
            SnoozelooTheme {
                val viewModel : MainViewModel = koinViewModel()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    innerPadding
                    viewModel.doSomething()
                    Log.w("TAG", "MainActivity doSomething")
                    RootGraph()
                }
            }
        }
    }
}