package com.example.rustorescreen.presentation.viewModel

/**
 * События списка приложений, передаваемые из UI в ViewModel.
 */
sealed interface AppListEvent {
    /**
     * Событие: пользователь нажал на иконку приложения.
     *
     * @param messageResId Идентификатор строкового ресурса для показа сообщения
     * (например, `R.string.*`) - для вывода сообщения - пасхалки.
     */
    data class TapOnIcon(val messageResId: Int) : AppListEvent
}