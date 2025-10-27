package com.example.rustorescreen.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rustorescreen.data.repositoryImpl.AppListRepositoryImpl
import com.example.rustorescreen.domain.useCase.GetAppListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppListViewModel : ViewModel() {
    private val getAppListUseCase = GetAppListUseCase(
        appRepository = AppListRepositoryImpl()
    )

    private val _state = MutableStateFlow<AppListState>(AppListState.Loading)
    val state : StateFlow<AppListState> = _state.asStateFlow()

    // TODO: events

    init {
        getAppList()
    }

    fun getAppList() {
        viewModelScope.launch {
            _state.value = AppListState.Loading // set loading state

            /*like try-catch*/
            runCatching {
                val appList = getAppListUseCase()

                _state.value = AppListState.Content(
                    appList = appList
                )
        }.onFailure {
                _state.value = AppListState.Error // set error state if exception occurs
            }
        }
    }
}