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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rustorescreen.R
import com.example.rustorescreen.domain.domainModel.AppDetails
import com.example.rustorescreen.presentation.viewModel.AppDetailsEvent
import com.example.rustorescreen.presentation.viewModel.AppDetailsState
import com.example.rustorescreen.presentation.viewModel.AppDetailsViewModel
import com.example.rustorescreen.presentation.viewModel.AppDetailsViewModelFactory
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class) // using Material3 experimental API
@Composable
fun AppDetailsScreen(
    app: AppDetails,
    onBack: () -> Unit
) {
    // получаение ViewModel с помощью фабрики, которая принимает id приложения
    val viewModel = viewModel<AppDetailsViewModel>(factory = AppDetailsViewModelFactory(app.id))
    /* подписка на состояние для реактивного обновления экрана приложения
    * обертка над AppDetailsState, которая вызывает перерисовку экрана когда состояние меняется
    * (и только тогда) */
    val state = viewModel.state.collectAsState() // подписка на состояние(дает реактивное обновление)

    val events : Flow<AppDetailsEvent> = viewModel.events
    val snackbarHostState : SnackbarHostState = remember{ SnackbarHostState() }
    ObserveEvents( // наблюдение за событиями из ViewModel и отображение Snackbar при необходимости
        events = events,
        snackbarHostState = snackbarHostState,
    )

    val context = LocalContext.current
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {Text(app.name, maxLines = 1) },
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
                        val shareText = "Check out this app: ${app.name}\n\n${app.description}"
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
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                        .padding(contentPadding),
                )
            }
        }
    }
}

/* ObserveEvents запускает корутину (LaunchedEffect) и делает events.collect.
 При получении события (например, WorkInProgress) вызывается snackbarHostState.showSnackbar.
  showSnackbar — suspend-функция => её корректно вызывать из корутины, запущенной в LaunchedEffect */
// наблюдение за событиями из ViewModel и отображение Snackbar при необходимости
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