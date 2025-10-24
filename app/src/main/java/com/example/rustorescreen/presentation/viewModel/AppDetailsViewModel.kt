package com.example.rustorescreen.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppDetailsViewModel: ViewModel() {

    private val getAppDetailsUseCase = GetAppDetailsUseCase(
        /* TODO: add DI there */

    )

    private val _state = MutableStateFlow<AppDetailsState>(AppDetailsState.Loading)
    val state: StateFlow<AppDetailsState> = _state.asStateFlow() //

    init {
        getAppDetails()
    }

    fun getAppDetails() {
        viewModelScope.launch {
            _state.value = AppDetailsState.Loading

            /*like try-catch*/
            runCatching {
                val appDetails = getAppDetailsUseCase()

                _state.value = AppDetailsState.Content(
                    appDetails = appDetails,
                    descriptionCollapsed = false,
                )
            }.onFailure {
                _state.value = AppDetailsState.Error // set error state if failure occurs
            }
        }
    }

    /*TODO: add events here (snacks)*/
}