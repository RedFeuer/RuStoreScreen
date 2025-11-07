package com.example.rustorescreen.presentation.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


/**
 * Точка входа приложения для Hilt.
 *
 * Аннотация `@HiltAndroidApp` инициализирует корневой компонент Hilt для
 * всего приложения и позволяет выполнять внедрение зависимостей в
 * `Application`, `Activity`, `Fragment` и другие Android-классы.
 */
@HiltAndroidApp
class App : Application()