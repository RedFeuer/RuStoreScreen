package com.example.rustorescreen.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.rustorescreen.presentation.viewModel.AppRoot
import com.example.rustorescreen.ui.theme.RuStoreScreenTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Главная Activity приложения — точка входа UI.
 *
 * Аннотация `@AndroidEntryPoint` разрешает Hilt внедрять зависимости в эту
 * Activity. Наследование от `ComponentActivity` используется для запуска
 * Jetpack Compose через `setContent`. В `onCreate` настраивается
 * edge\-to\-edge и отображается корневой Compose\-компонент `AppRoot`.
 */
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