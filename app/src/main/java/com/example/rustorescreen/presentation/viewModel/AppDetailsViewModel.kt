package com.example.rustorescreen.presentation.viewModel

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rustorescreen.R
import com.example.rustorescreen.domain.useCase.GetAppDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AppDetailsViewModel  @Inject constructor (
    private val getAppDetailsUseCase: GetAppDetailsUseCase,
    savedStateHandle: SavedStateHandle, // для получения сохраненного состояния(включая параметры навигации)
): ViewModel() {
    private val appId: String = checkNotNull(savedStateHandle.get<String>("appId"))

    private val _state = MutableStateFlow<AppDetailsState>(AppDetailsState.Loading)
    val state: StateFlow<AppDetailsState> = _state.asStateFlow()

    private val _events = Channel<AppDetailsEvent>(
        capacity = BUFFERED,
        )
    val events = _events.receiveAsFlow()

    init {
        getAppDetails()
    }

    // показать сообщение о том, что функция в разработке
    fun showWorkInProgressMessage(@StringRes messageResId: Int = R.string.work_in_progress) {
        viewModelScope.launch{
            _events.send(AppDetailsEvent.WorkInProgress(messageResId)) // отправка события в канал
        }
    }

    fun expandDescription() {
        _state.update { currentState ->
            if (currentState is AppDetailsState.Content) {
                currentState.copy(descriptionExpanded = !currentState.descriptionExpanded)
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