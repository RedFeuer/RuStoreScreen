package com.example.rustorescreen.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rustorescreen.data.repositoryImpl.AppDetailsRepositoryImpl
import com.example.rustorescreen.domain.useCase.GetAppDetailsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppDetailsViewModel(private val appId: Int): ViewModel() {

    private val getAppDetailsUseCase = GetAppDetailsUseCase(
        /* TODO: add DI there */
        appDetailsRepository = AppDetailsRepositoryImpl()
    )

    private val _state = MutableStateFlow<AppDetailsState>(AppDetailsState.Loading)
    val state: StateFlow<AppDetailsState> = _state.asStateFlow()

    private val _events = Channel<AppDetailsEvent>(BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        getAppDetails()
    }

    /*TODO: add events here (snacks) - в курсе это показ таблички, что функция WIP*/

    fun expandDescription() {
        _state.update { currentState ->
            if (currentState is AppDetailsState.Content) {
                currentState.copy(descriptionExpanded = true)
            } else {
                currentState
            }
        }
    }

    fun getAppDetails() {
        viewModelScope.launch {
            _state.value = AppDetailsState.Loading // set loading state

            /*like try-catch*/
            runCatching {
                val appDetails = getAppDetailsUseCase(appId) // fetch app details using the use case

                _state.value = AppDetailsState.Content(
                    appDetails = appDetails,
                    descriptionExpanded = false,
                )
            }.onFailure {
                _state.value = AppDetailsState.Error // set error state if failure occurs
            }
        }
    }
}