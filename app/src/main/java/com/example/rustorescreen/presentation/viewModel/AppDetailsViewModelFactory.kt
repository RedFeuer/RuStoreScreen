package com.example.rustorescreen.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// фабрика для создания экземпляров AppDetailsViewModel с параметром appId
//class AppDetailsViewModelFactory(private val appId: Int) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(AppDetailsViewModel::class.java)) { // проверяем, что запрашиваемый класс является AppDetailsViewModel
//            @Suppress("UNCHECKED_CAST") // подавляем предупреждение о небезопасном приведении типов
//            return AppDetailsViewModel(appId) as T // создаем и возвращаем экземпляр AppDetailsViewModel с переданным appId
//        }
//        throw IllegalArgumentException("Unknown ViewModel class") // выбрасываем исключение, если запрашиваемый класс неизвестен
//    }
//}
