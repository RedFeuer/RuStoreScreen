package com.example.rustorescreen.presentation.viewModel

import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rustorescreen.data.repositoryImpl.AppListRepositoryImpl
import com.example.rustorescreen.domain.useCase.GetAppListUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import com.example.rustorescreen.R

class AppListViewModel : ViewModel() {
    private val getAppListUseCase = GetAppListUseCase(
        appRepository = AppListRepositoryImpl()
    )

    private val _state = MutableStateFlow<AppListState>(AppListState.Loading)
    val state : StateFlow<AppListState> = _state.asStateFlow()

    // TODO: events
    private val _events = Channel<AppListEvent> (
        capacity = BUFFERED,
    )
    val events = _events.receiveAsFlow()

    init {
        getAppList()
    }

    fun showTapOnIconMessage(@StringRes messageResId: Int = R.string.easter_egg_message) {
        viewModelScope.launch {
            _events.send(AppListEvent.TapOnIcon(messageResId)) // отправка события в канал
        }
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