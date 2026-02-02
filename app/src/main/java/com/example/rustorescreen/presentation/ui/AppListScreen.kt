package com.example.rustorescreen.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.rustorescreen.R
import com.example.rustorescreen.domain.domainModel.AppDetails
import com.example.rustorescreen.presentation.viewModel.AppListEvent
import com.example.rustorescreen.presentation.viewModel.AppListState
import com.example.rustorescreen.presentation.viewModel.AppListViewModel
import kotlinx.coroutines.flow.Flow

/**
 * Отображает экран со списком приложений.
 *
 * Подписывается на `AppListViewModel` через Hilt и:
 * - наблюдает состояние UI (`viewModel.state`) и отображает соответствующий контент:
 *   * `AppListState.Loading` — индикатор загрузки,
 *   * `AppListState.Error` — экран ошибки с кнопкой обновления,
 *   * `AppListState.Content` — список приложений.
 * - подписывается на поток одноразовых событий (`viewModel.events`) и показывает `Snackbar`
 *   через переданный `SnackbarHostState` при необходимости.
 *
 * @param onAppClick Колбек, вызываемый при клике на элемент списка. Получает `app.id`. Переход на экран конкретного приложения
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppListScreen(
    onAppClick: (String) -> Unit
) {
    val viewModel = hiltViewModel<AppListViewModel>()
    val state = viewModel.state.collectAsState() // подписка на состояние(дает реактивное обновление)

    val events : Flow<AppListEvent> = viewModel.events
    val snackbarHostState : SnackbarHostState = remember{ SnackbarHostState() }

    ObserveEvents( // наблюдение за событиями из ViewModel и отображение Snackbar при необходимости
        events = events,
        snackbarHostState = snackbarHostState,
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("RuStore") })
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { contentPadding ->
        when (val currentState = state.value) {
            is AppListState.Loading -> {
                AppListLoading(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding),
                )
            }

            is AppListState.Error -> {
                AppListError(
                    onRefreshClick = { viewModel.getAppList() },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding),
                )
            }

            is AppListState.Content -> {
                AppListContent(
                    apps = currentState.appList,
                    onAppClick = onAppClick,
                    onIconClick = { viewModel.showTapOnIconMessage() },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                )
            }
        }
    }
}

/**
 * Наблюдает одноразовые события из ViewModel и показывает Snackbar при необходимости.
 *
 * Подписывается на переданный [events] внутри [LaunchedEffect]. При получении события
 * [AppListEvent.TapOnIcon] отображает сообщение в [snackbarHostState].
 * При необходимости можно добавлять сюда новые события.
 *
 * @param events поток одноразовых событий, испускаемых ViewModel (например, [AppListEvent]).
 * @param snackbarHostState состояние для отображения Snackbar.
 */
@Composable
private fun ObserveEvents(
    events: Flow<AppListEvent>,
    snackbarHostState: SnackbarHostState,
) {
    val easterEggMessage = stringResource(R.string.easter_egg_message)

    LaunchedEffect(Unit) {
        events.collect {event ->
            when (event) {
                is AppListEvent.TapOnIcon -> {
                    snackbarHostState.showSnackbar(message = easterEggMessage)
                }
            }
        }
    }
}

/**
 * Отображает контент со списком приложений.
 *
 * Использует [LazyColumn] для рендеринга переданного списка [apps]. При клике на элемент
 * вызывается [onAppClick] с `app.id`. При клике на иконку приложения вызывается [onIconClick].
 *
 * @param apps список данных типа [AppDetails] для отображения.
 * @param onAppClick колбек, вызываемый при клике на элемент списка, принимает `id` приложения.
 * @param onIconClick колбек, вызываемый при клике на иконку приложения.
 * @param modifier модификатор для корневого контейнера.
 */
@Composable
private fun AppListContent(
    apps: List<AppDetails>,
    onAppClick: (String) -> Unit,
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(apps, key = {it.id}) { app ->
            AppRow(
                app = app,
                onAppClick = { onAppClick(app.id) },
                onIconClick = { onIconClick() }
            )
            HorizontalDivider()
        }
    }
}

/**
 * Экран ошибки загрузки списка приложений.
 *
 * Показывает сообщение об ошибке и кнопку обновления. При нажатии на кнопку вызывается
 * [onRefreshClick], что позволяет повторно запросить список.
 *
 * @param onRefreshClick колбек для повторной попытки загрузки.
 * @param modifier модификатор для корневого контейнера.
 */
@Composable
private fun AppListError(
    onRefreshClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.app_details_error),
            fontStyle = MaterialTheme.typography.headlineMedium.fontStyle,
        )

        Spacer(Modifier.height(12.dp))

        // Refresh button
        Button(
            onClick = onRefreshClick,
        ) {
            Text(text = stringResource(R.string.app_details_error_refresh) )
        }
    }
}

/**
 * Центрированный индикатор загрузки.
 *
 * Отображает [CircularProgressIndicator] по центру предоставленного контейнера.
 *
 * @param modifier модификатор для контейнера индикатора.
 */
@Composable
private fun AppListLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

/**
 * Отображает одну строку списка приложения.
 *
 * Компонент показывает:
 * - иконку приложения (кликабельна, вызывает `onIconClick`),
 * - название, описание и категорию приложения.
 * Вся строка кликабельна и вызывает `onAppClick` — используется для перехода
 * на экран конкретного приложения.
 *
 * @param app данные приложения [AppDetails]
 * @param onAppClick вызывается при клике на всю строку (передаётся для навигации)
 * @param onIconClick вызывается при клике на иконку (передается для отображения пасхалки)
 */
@Composable
private fun AppRow(
    app: AppDetails,
    onAppClick: () -> Unit,
    onIconClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onAppClick)
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = app.iconUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clickable(onClick = onIconClick)
                .size(80.dp)
                .clip(RoundedCornerShape(4.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = app.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = app.description, style = MaterialTheme.typography.bodyMedium)
            Text(text = app.category.toString(), style = MaterialTheme.typography.bodySmall)
        }
    }
}