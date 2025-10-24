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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rustorescreen.domain.domainModel.AppDetails
import com.example.rustorescreen.presentation.viewModel.AppDetailsState
import com.example.rustorescreen.presentation.viewModel.AppDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class) // using Material3 experimental API
@Composable
fun AppDetailsScreen(
    app: AppDetails,
    onBack: () -> Unit
) {
    val viewModel = viewModel<AppDetailsViewModel>()
    /* подписка на состояние для реактивного обновления экрана приложения
    * обертка над AppDetailsState, которая вызывает перерисовку экрана когда состояние меняется
    * (и только тогда) */
    val state = viewModel.state.collectAsState()

    // TODO: add events


    val context = LocalContext.current
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {Text(app.name, maxLines = 1) },
                /* button to go back to list of apps */
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
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
                        Icon(Icons.Default.Share, contentDescription = "Поделиться")
                    }
                }
            )
        }
    ) { contentPadding -> // inner padding from Scaffold
        when (val currentState = state) {
            is AppDetailsState.Loading ->{
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
                        onBack
                    },
                    onShareClick = {
                        // TODO: Реализовать функционал шаринга
                        onBack
                    },
                    onInstallClick = {
                        // TODO: Реализовать функционал установки приложения
                        onBack
                    },
                    onDescriptionExpandClick = {
                        // TODO: Реализовать разворачивание/сворачивание описания
                        viewModel.ExpandDescription()
                    },
                    onDeveloperInfoClick = {
                        // TODO: Реализовать переход к информации о разработчике
                        onBack
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