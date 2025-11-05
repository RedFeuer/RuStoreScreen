package com.example.rustorescreen.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.rustorescreen.presentation.viewModel.AppRoot
import com.example.rustorescreen.ui.theme.RuStoreScreenTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RuStoreScreenTheme {
                AppRoot()
            }
        }
    }
}