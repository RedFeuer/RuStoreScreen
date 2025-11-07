package com.example.rustorescreen.util

/**
 * Простая абстракция для логирования в приложении.
 *
 * Реализации могут направлять сообщения в Logcat, внешние SDK или файлы.
 */
interface Logger {
    /**
     * Выводит отладочное (debug) сообщение.
     *
     * @param message текст сообщения
     */
    fun d(message: String)

    /**
     * Выводит сообщение об ошибке.
     *
     * @param message текст сообщения
     * @param throwable необязательное(nullable) исключение, связанное с ошибкой
     */
    fun e(message: String, throwable: Throwable? = null)
}