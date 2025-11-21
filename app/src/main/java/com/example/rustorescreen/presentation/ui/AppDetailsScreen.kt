package com.example.rustorescreen.presentation.ui

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.rustorescreen.R
import com.example.rustorescreen.domain.domainModel.AppDetails
import com.example.rustorescreen.presentation.viewModel.AppDetailsEvent
import com.example.rustorescreen.presentation.viewModel.AppDetailsState
import com.example.rustorescreen.presentation.viewModel.AppDetailsViewModel
import kotlinx.coroutines.flow.Flow

/**
 * Экран конкретного приложения.
 *
 * Отображает разные состояния на основе [viewModel.state]:
 *  - [AppDetailsState.Loading] — индикатор загрузки;
 *  - [AppDetailsState.Error] — экран ошибки с кнопкой обновления;
 *  - [AppDetailsState.Content] — основное содержимое с деталями приложения.
 *
 * Подписывается(реактивно - меняется только при изменении) на поток событий [viewModel.events]
 * и отображает Snackbar через переданный [snackbarHostState] при получении релевантных событий
 * (см. [ObserveEvents]).
 *
 * @param app Вспомогательные данные приложения, показываются как заглушка до получения полного [AppDetails].
 * @param viewModel ViewModel, содержащая состояние экрана и поток событий(Flow<Event>).
 * @param onBack Лямбда, выполняемая при запросе возврата (нажатие кнопки "назад").
 */
@OptIn(ExperimentalMaterial3Api::class) // using Material3 experimental API
@Composable
fun AppDetailsScreen(
    app: AppDetails?,
    viewModel: AppDetailsViewModel,
    onBack: () -> Unit
) {
    val state = viewModel.state.collectAsState() // подписка на состояние(дает реактивное обновление)

    val events : Flow<AppDetailsEvent> = viewModel.events
    val snackbarHostState : SnackbarHostState = remember{ SnackbarHostState() }
    ObserveEvents( // наблюдение за событиями из ViewModel и отображение Snackbar при необходимости
        events = events,
        snackbarHostState = snackbarHostState,
    )

    val context = LocalContext.current

    val appName = when (val currentState = state.value) { // получение имени приложения в зависимости от состояния
        is AppDetailsState.Content -> currentState.appDetails.name
        else -> app?.name ?: "" // если app null, то пустая строка
    }
    val appDescription = when (val currentState = state.value) { // получение описания приложения в зависимости от состояния
        is AppDetailsState.Content -> currentState.appDetails.description
        else -> app?.description ?: "" // если app null, то пустая строка
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {Text(appName, maxLines = 1) },
                /* button to go back to list of apps */
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                actions = { // share button
                    IconButton(onClick =  {
                        val shareText = "Check out this app: ${appName}\n\n${appDescription}"
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, shareText)
                        }
                        context.startActivity(
                            Intent.createChooser(shareIntent, "Поделиться с помощью")
                        )
                    }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = stringResource(R.string.share),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
    ) { contentPadding -> // inner padding from Scaffold
        when (val currentState = state.value) {
            is AppDetailsState.Loading -> {
                AppDetailsLoading(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                        .padding(contentPadding),
                )
            }

            is AppDetailsState.Error -> {
                AppDetailsError(
                    onRefreshClick = { viewModel.getAppDetails() },
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                        .padding(contentPadding),
                )
            }

            is AppDetailsState.Content -> {
                AppDetailsContent(
                    content = currentState,
                    onBackClick = {
                        // TODO: Открыть предыдущий экран через Jetpack Navigation
                        onBack()
                    },
                    onShareClick = {
                        // TODO: Реализовать функционал шаринга
                        viewModel.showWorkInProgressMessage()
                    },
                    onInstallClick = {
                        // TODO: Реализовать функционал установки приложения
                        viewModel.showWorkInProgressMessage()
                    },
                    onDescriptionExpandClick = {
                        // TODO: Реализовать разворачивание/сворачивание описания
                        viewModel.expandDescription()
                    },
                    onDeveloperInfoClick = {
                        // TODO: Реализовать переход к информации о разработчике
                        viewModel.showWorkInProgressMessage()
                    },
                    /* отложенная передача параметра AppCategory через лямбду
                    * передаем лямбду (AppCategory) -> Unit */
                    onCategoryUpdateClick = { newCategory ->
                        viewModel.updateAppCategory(newCategory)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                        .padding(contentPadding),
                )
            }
        }
    }
}

/**
 * Подписка на поток событий UI [events] и отображение Snackbar при получении событий.
 *
 * Реализовано через [LaunchedEffect].
 * ObserveEvents запускает корутину (LaunchedEffect) и делает events.collect.
 * При получении события (например, WorkInProgress) вызывается snackbarHostState.showSnackbar.
 * [SnackbarHostState.showSnackbar] — suspend-функция и должна вызываться из корутины.
 * Текст для Snackbar берётся из ресурсов через [stringResource].
 *
 * @param events Поток событий типа [AppDetailsEvent] из ViewModel.
 * @param snackbarHostState Хост для показа Snackbar.
 */
@Composable
private fun ObserveEvents(
    events: Flow<AppDetailsEvent>,
    snackbarHostState: SnackbarHostState,
) {
    val workInProgressText: String = stringResource(R.string.work_in_progress)

    LaunchedEffect(Unit) {
        events.collect { event -> // чтение поступающих событий из Flow
            when (event) {
                is AppDetailsEvent.WorkInProgress -> {
                    snackbarHostState.showSnackbar(message = workInProgressText)
                }
            }
        }
    }
}