package com.example.rustorescreen.di

import com.example.rustorescreen.data.api.AppListAPI
import com.example.rustorescreen.data.local.AppDetailsDao
import com.example.rustorescreen.data.local.AppDetailsEntityMapper
import com.example.rustorescreen.data.mapper.AppDetailsMapper
import com.example.rustorescreen.data.repositoryImpl.AppDetailsRepositoryImpl
import com.example.rustorescreen.domain.repositoryInterface.AppDetailsRepository
import com.example.rustorescreen.domain.useCase.GetAppDetailsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/* TODO: ПЕРЕПИСАТЬ ДОКУМЕНТАЦИЮ */
/**
 * Hilt\-модуль для зависимостей, связанных с конкретным приложением.
 *
 * Привязывает реализацию репозитория и предоставляет провайдеры:
 * - GetAppDetailsUseCase
 * - AppDetailsMapper
 * - HttpLoggingInterceptor / OkHttpClient / RetrofitAppDetailsApi / AppListAPI
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
object AppDetailsModule {

    @Provides
    @Singleton
    fun provideAppDetailsRepository(
        api: AppListAPI,
        dao: AppDetailsDao,
        mapper: AppDetailsMapper,
        entityMapper: AppDetailsEntityMapper,
    ): AppDetailsRepository {
        return AppDetailsRepositoryImpl(
            appListApi = api,
            dao = dao,
            appDetailsMapper = mapper,
            appDetailsEntityMapper = entityMapper,
        )
    }

    @Provides
    @Singleton
    fun provideGetAppDetailsUseCase(repository: AppDetailsRepository): GetAppDetailsUseCase {
        return GetAppDetailsUseCase(repository)
    }

    /**
     * Предоставляет маппер для преобразования моделей данных [AppDetailsDto]
     * в доменные объекты [AppDetails] и обратно.
     */
    @Provides
    @Singleton
    fun provideAppDetailsMapper(): AppDetailsMapper {
        return AppDetailsMapper()
    }

    /**
     * Предоставляет интерцептор для логирования HTTP-запросов/ответов.
     *
     * Уровень логирования установлен в BODY для подробного вывода
     * Вообще в проде в релизных сборках это надо убирать!!!!!
     */
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    /**
     * Создаёт и конфигурирует [OkHttpClient] с интерцептором логирования и таймаутами.
     *
     * @param logging интерцептор для логирования HTTP-трафика
     * @return настроенный экземпляр [OkHttpClient]
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build()
    }

    /**
     * Строит экземпляр [Retrofit] для общения с сервером приложений.
     *
     * Использует базовый URL `http://185.103.109.134` и
     * сериализацию kotlinx.serialization(asConverterFactory).
     *
     * @param client настроенный HTTP-клиент
     * @return экземпляр [Retrofit] для [AppListAPI]
     */
    @Provides
    @Singleton
    fun provideRetrofitAppDetailsAPI(client: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        val json = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .client(client) // перехватчик HTTP-запросов
            .baseUrl("http://185.103.109.134") // Базовый URL для API
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    /**
     * Создаёт реализацию интерфейса [AppListAPI] на основе [Retrofit].
     */
    @Provides
    @Singleton
    fun provideAppListApi(retrofit: Retrofit): AppListAPI {
        return retrofit.create(AppListAPI::class.java)
    }
}