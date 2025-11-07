package com.example.rustorescreen.di

import com.example.rustorescreen.data.repositoryImpl.AppListRepositoryImpl
import com.example.rustorescreen.domain.repositoryInterface.AppListRepository
import com.example.rustorescreen.domain.useCase.GetAppListUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt\-модуль для зависимостей, связанных с получением списка приложений.
 *
 * Привязывает реализацию репозитория и предоставляет провайдеры:
 * - GetAppListUseCase
 *
 * Класс объявлен абстрактным, поскольку содержит `@Binds`\-методы;
 * конкретные `@Provides` размещены в `companion object`.
 *
 * Использование `@Binds` и `@Provides` поддерживает IoC и реализует
 * принцип инверсии зависимостей (Dependency Inversion Principle):
 * зависимости предоставляются через интерфейсы и внедряются извне.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AppListModule {

    /**
     * Привязывает конкретную реализацию репозитория к его интерфейсу.
     *
     * Hilt будет использовать этот бинд, чтобы при запросе [AppListRepository]
     * предоставлять экземпляр [AppListRepositoryImpl].
     *
     * @param impl конкретная реализация репозитория, предоставляемая Hilt
     * @return интерфейс [AppListRepository], который будет внедряться в зависимые классы
     */
    @Binds // Биндим реализацию репозитория к его интерфейсу
    @Singleton
    abstract fun bindAppListRepository(impl: AppListRepositoryImpl): AppListRepository

    /**
     * Статический блок для `@Provides`-методов. Используется для создания зависимостей,
     * которые нельзя пометить `abstract`/`@Binds`.
     */
    companion object {
        /**
         * Предоставляет синглтон-экземпляр [GetAppListUseCase].
         *
         * Hilt будет разрешать зависимость [AppListRepository] и передавать её в этот метод.
         *
         * @param repository репозиторий для получения списка приложений
         * @return синглтон-экземпляр [GetAppListUseCase]
         */
        @Provides
        @Singleton
        fun provideGetAppListUseCase(repository: AppListRepository): GetAppListUseCase {
            return GetAppListUseCase(repository)
        }
    }
}