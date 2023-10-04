package com.itami.stockterminal.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.itami.stockterminal.presentation.ui.theme.StockTerminalTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StockTerminalTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                val viewModel: TerminalViewModel = viewModel()
                val screenState = viewModel.state.collectAsState()

                LaunchedEffect(key1 = true) {
                    viewModel.uiEvent.collect { event ->
                        when (event) {
                            is TerminalScreenUiEvent.OnShowSnackbar -> {
                                snackbarHostState.showSnackbar(event.message)
                            }
                        }
                    }
                }

                Scaffold(
                    snackbarHost = {
                        SnackbarHost(snackbarHostState)
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        when (val state = screenState.value) {
                            is TerminalScreenState.Content -> {
                                Terminal(bars = state.bars)
                            }

                            is TerminalScreenState.Initial -> {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
