package com.example.rustorescreen.presentation.viewModel

/**
 * События, исходящие из ViewModel-слоя конкретного приложения.
 *
 * Используется для передачи одноразовых событий в UI (например, показать сообщение).
 */
sealed interface AppDetailsEvent{
    /**
     * Событие, означающее, что функциональность пока в разработке.
     *
     * @param messageResId строка (\`@StringRes\`), которую следует показать пользователю.
     */
    data class WorkInProgress(val messageResId: Int) : AppDetailsEvent
}