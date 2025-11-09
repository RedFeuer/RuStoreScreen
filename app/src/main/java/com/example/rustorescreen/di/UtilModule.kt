package com.example.rustorescreen.di

import com.example.rustorescreen.util.Logger
import com.example.rustorescreen.util.LoggerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Hilt\-модуль, предоставляющий утилитарные зависимости приложения.
 *
 * Установлен в [SingletonComponent], поэтому зависимости из этого модуля
 * живут в пределах жизненного цикла приложения (Singleton).
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class UtilModule {
    /**
     * Привязывает реализацию [LoggerImpl] к интерфейсу [Logger].
     *
     * Аннотация @Singleton указывает, что будет создан один экземпляр
     * [LoggerImpl] на весь срок работы приложения.
     *
     * @param impl конкретная реализация, предоставляемая Hilt
     * @return интерфейс, используемый зависимостями
     */
    @Binds
    @Singleton
    abstract fun bindLogger(impl: LoggerImpl): Logger
}