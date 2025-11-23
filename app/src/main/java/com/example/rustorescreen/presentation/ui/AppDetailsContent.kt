package com.example.rustorescreen.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rustorescreen.domain.domainModel.AppCategory
import com.example.rustorescreen.domain.domainModel.AppDetails
import com.example.rustorescreen.presentation.viewModel.AppDetailsState

/**
 * Отображает содержимое экрана конкретного приложения.
 *
 * Столбец с возможностью вертикальной прокрутки содержит:
 * - заголовок приложения;
 * - кнопку установки;
 * - список скриншотов;
 * - описание приложения (с возможностью разворачивания/сворачиваия);
 * - раздел с информацией о разработчике.
 *
 * @param content текущее состояние экрана (`AppDetailsState.Content`), содержит `appDetails` и `descriptionExpanded`.
 * @param onBackClick вызов при нажатии "назад" (в UI пока закомментирован, так как используется Jetpack Navigation).
 * @param onShareClick вызов при нажатии "поделиться". (в UI пока закомментирован, так как используется Jetpack Navigation).
 * @param onInstallClick вызов при нажатии кнопки установки.(WIP)
 * @param onDescriptionExpandClick вызов при переключении состояния описания (развернуть/свернуть).
 * @param onDeveloperInfoClick вызов при нажатии на информацию о разработчике.(WIP)
 * @param modifier дополнительный [Modifier] для внешней настройки компоновки.
 */
@Composable
fun AppDetailsContent(
    content: AppDetailsState.Content,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onInstallClick: () -> Unit,
    onDescriptionExpandClick: () -> Unit,
    onDeveloperInfoClick: () -> Unit,
    onCategoryUpdateClick: (AppCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    val appDetails: AppDetails = content.appDetails
    val descriptionExpanded: Boolean = content.descriptionExpanded

    val scrollState = rememberScrollState()

    Column(modifier = modifier.verticalScroll(scrollState)) {
//        Toolbar(
//            onBackClick = onBackClick,
//            onShareClick = onShareClick,
//        )
//        Spacer(modifier = Modifier.height(4.dp))

        AppDetailsHeader(
            appDetails = appDetails,
            onCategoryUpdateClick = onCategoryUpdateClick,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        Spacer(Modifier.height(4.dp))

        InstallButton(
            content = content,
            onClick = onInstallClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        Spacer(Modifier.height(4.dp))

        ScreenshotsList(
            screenshotUrlList = appDetails.screenshotUrlList,
            contentPadding = PaddingValues(horizontal = 16.dp),
        )
        Spacer(Modifier.height(4.dp))

        AppDescription(
            description = appDetails.description,
            expanded = !descriptionExpanded,
            onExpandClick = onDescriptionExpandClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        Spacer(Modifier.height(4.dp))

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.outlineVariant,
        )
        Spacer(Modifier.height(4.dp))

        Developer(
            name = appDetails.developer,
            onClick = onDeveloperInfoClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
        )
    }
}