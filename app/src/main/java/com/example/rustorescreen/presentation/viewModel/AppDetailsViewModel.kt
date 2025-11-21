package com.example.rustorescreen.presentation.viewModel

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rustorescreen.R
import com.example.rustorescreen.domain.domainModel.AppCategory
import com.example.rustorescreen.domain.useCase.GetAppDetailsUseCase
import com.example.rustorescreen.domain.useCase.UpdateAppCategoryUseCase
import com.example.rustorescreen.util.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
    private val updateAppCategoryUseCase: UpdateAppCategoryUseCase,
    private val logger: Logger,
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
     * Загрузка конкретного приложения
     *
     * Поведение:
     * - Устанавливает `AppDetailsState.Loading` синхронно при вызове метода.
     * - Подписывается на возвращаемый `Flow<AppDetails>` и обрабатывает каждый эмит через `onEach`.
     * - Для каждого полученного `AppDetails` устанавливает `AppDetailsState.Content(appDetails, descriptionExpanded = false)`.
     * - Ошибки, возникшие в потоке до оператора `catch`, переводят состояние в `AppDetailsState.Error`.
     * - Подписка запускается методом `launchIn(viewModelScope)` и живёт в рамках `viewModelScope` — будет отменена при уничтожении ViewModel.
     */
    fun getAppDetails() {
        _state.value = AppDetailsState.Loading // синхронно сразу выставляем состояние загрузки
        getAppDetailsUseCase(appId)
            .onEach { appDetails ->
                _state.value = AppDetailsState.Content( // устанавливаем экран конкретного приложения
                    appDetails = appDetails,
                    descriptionExpanded = false,
                )
            }
            .catch { error ->
                logger.e(message = "Failed to load app", throwable = error) // логируем ошибку в Logcat
                _state.value = AppDetailsState.Error // экран ошибки
            }
            /* запуск корутины внутри viewModelScope для ассинхронного обновления интерфейса
            * позволяет реактивно подписаться на состояние экрана конкретного приложения */
            .launchIn(viewModelScope)
    }

    fun updateAppCategory(newCategory: AppCategory) {
        viewModelScope.launch {
            try {
                updateAppCategoryUseCase(id = appId, newCategory = newCategory)

                val current = (_state.value as? AppDetailsState.Content)?.appDetails
                if (current != null) {
                    val updated = current.copy(category = newCategory)
                    _state.value = AppDetailsState.Content(
                        appDetails = updated, // сразу устанавливаем новую категорию
                        descriptionExpanded = (_state.value as? AppDetailsState.Content)?.descriptionExpanded ?: false
                    )
                }
            }
            catch(e: Exception) {
                logger.e(message = "Failed to update appCategory", throwable = e)
                _state.value = AppDetailsState.Error
            }
        }
    }
}