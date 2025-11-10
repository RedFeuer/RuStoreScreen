package com.example.rustorescreen.presentation.viewModel

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rustorescreen.R
import com.example.rustorescreen.domain.domainModel.AppDetails
import com.example.rustorescreen.domain.useCase.GetAppDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * ViewModel для экрана конкретного приложения.
 *
 * Отвечает за загрузку данных приложения по `appId`, хранение состояния экрана и отправку одноразовых событий.
 *
 * @param getAppDetailsUseCase use-case для получения конкретного приложения по его id.
 * @param savedStateHandle используется для получения `appId`, переданного через навигацию.
 */
@HiltViewModel
class AppDetailsViewModel  @Inject constructor (
    private val getAppDetailsUseCase: GetAppDetailsUseCase,
    savedStateHandle: SavedStateHandle, // для получения сохраненного состояния(включая параметры навигации)
): ViewModel() {
    /**
     * Идентификатор приложения, полученный из `SavedStateHandle`.
     * @throws IllegalStateException если `appId` отсутствует
     */
    private val appId: String = checkNotNull(savedStateHandle.get<String>("appId"))


    /**
     * Внутренний StateFlow, содержащий текущее состояние экрана.
     * Инициализируется состоянием Loading до завершения загрузки.
     */
    private val _state = MutableStateFlow<AppDetailsState>(AppDetailsState.Loading)

    /**
     * Публичный неизменяемый [StateFlow] для подписки из UI.
     */
    val state: StateFlow<AppDetailsState> = _state.asStateFlow()


    /**
     * Канал для одноразовых событий (snackbar, навигация и т.п.).
     * Используется буферизованный канал — отправка не будет блокировать корутину, пока в буфере есть свободное место.
     */
    private val _events = Channel<AppDetailsEvent>(
        capacity = BUFFERED,
    )

    /**
     * Flow одноразовых событий для подписки в UI.
     */
    val events = _events.receiveAsFlow()

    /**
     * При создании ViewModel запускаем загрузку конкретного приложения.
     */
    init {
        getAppDetails()
    }

    /**
     * Отправляет одноразовое сообщение о том, что функция в разработке.
     *
     * @param messageResId сообщение (по умолчанию [R.string.work_in_progress] - функция в разработке).
     */
    fun showWorkInProgressMessage(@StringRes messageResId: Int = R.string.work_in_progress) {
        viewModelScope.launch{
            _events.send(AppDetailsEvent.WorkInProgress(messageResId)) // отправка события в канал
        }
    }

    /**
     * Меняет состояние развернутое/свернутое описание у текущего приложения.
     * Ничего не делает, если состояние экрана не `AppDetailsState.Content`.
     */
    fun expandDescription() {
        _state.update { currentState ->
            if (currentState is AppDetailsState.Content) {
                currentState.copy(descriptionExpanded = !currentState.descriptionExpanded)
            } else {
                currentState
            }
        }
    }

    /**
     * Асинхронно загружает конкретное приложение и обновляет состояние:
     * - устанавливает Loading перед запросом;
     * - при успехе — AppDetailsState.Content с полученными данными;
     * - при ошибке — AppDetailsState.Error.
     */
    fun getAppDetails() {
        viewModelScope.launch {
            _state.value = AppDetailsState.Loading // set loading state

            /*like try-catch*/
            runCatching {
                val appDetailsFlow: Flow<AppDetails> = getAppDetailsUseCase(appId) // fetch app details using the use case
                val appDetails: AppDetails = appDetailsFlow.first()

                _state.value = AppDetailsState.Content(
                    appDetails = appDetails,
                    descriptionExpanded = false, // description collapsed in default
                )
            }.onFailure {
                _state.value = AppDetailsState.Error // set error state if failure occurs
            }
        }
    }
}