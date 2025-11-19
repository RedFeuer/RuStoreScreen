package com.example.rustorescreen.presentation.viewModel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rustorescreen.R
import com.example.rustorescreen.domain.domainModel.AppDetails
import com.example.rustorescreen.domain.useCase.GetAppListUseCase
import com.example.rustorescreen.util.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * ViewModel для экрана списка приложений.
 *
 * Управляет состоянием UI через [state] и одноразовыми событиями через [events].
 *
 * @param getAppListUseCase UseCase, возвращающий список приложений.
 * @param logger Логгер для записи ошибок и отладочной информации в Logcat.
 */
@HiltViewModel
/**
 * ViewModel для экрана списка приложений.
 *
 * Управляет состоянием UI через [state] и одноразовыми событиями через [events].
 *
 * @param getAppListUseCase UseCase, возвращающий список приложений.
 * @param logger Логгер для записи ошибок и отладочной информации в Logcat.
 */
class AppListViewModel @Inject constructor(
    private val getAppListUseCase: GetAppListUseCase,
    private val logger: Logger, // чтобы логировать проблемы при подгрузке и работе с API в Logcat
) : ViewModel() {

    /** Приватный поток состояния экрана. Изначально устанавливается в [AppListState.Loading]. */
    private val _state = MutableStateFlow<AppListState>(AppListState.Loading)

    /** Публичный `StateFlow` для подписки UI на изменения состояния(т.н. реактивной подписки). */
    val state : StateFlow<AppListState> = _state.asStateFlow()


    /** Приватный канал для одноразовых событий экрана */
    private val _events = Channel<AppListEvent> (
        capacity = BUFFERED,
    )

    /** Публичный flow одноразовых событий для подписки UI. */
    val events = _events.receiveAsFlow()

    /**
     * При создании ViewModel запускаем загрузку списка приложений.
     */
    init {
        getAppList()
    }

    /**
     * Посылает одноразовое событие-подсказку при тапе на иконку приложения.
     *
     * @param messageResId Ресурс строки с сообщением, по умолчанию `R.string.easter_egg_message`(пасхалка).
     */
    fun showTapOnIconMessage(@StringRes messageResId: Int = R.string.easter_egg_message) {
        viewModelScope.launch {
            _events.send(AppListEvent.TapOnIcon(messageResId)) // отправка события в канал
        }
    }

    /**
     * Загрузка списка приложений.
     *
     * Поведение:
     * - Устанавливает состояние в `AppListState.Loading` синхронно при вызове.
     * - Подписывается на возвращаемый `Flow<List<AppDetails>>` и обрабатывает каждый эмит.
     * - Ошибки, возникшие в потоке, логируются через `logger.e` и переводят состояние в `AppListState.Error`.
     * - Подписка запускается с помощью `launchIn(viewModelScope)` и живёт в рамках `viewModelScope` — будет отменена при уничтожении ViewModel.
     */
    fun getAppList() {
        _state.value = AppListState.Loading // синхронно сразу выставляем состояние загрузки
        getAppListUseCase()
            .onEach { appList ->
                _state.value = AppListState.Content(appList = appList) // устанавливаем экран списка приложений
            }
            .catch { error ->
                logger.e(message = "Failed to load app list", throwable = error) // логируем ошибку в Logcat
                _state.value = AppListState.Error // экран ошибки
            }
            /* запуск корутины внутри viewModelScope для ассинхронного обновления интерфейса
           * позволяет реактивно подписаться на состояние экрана конкретного приложения */
            .launchIn(viewModelScope)
    }
}