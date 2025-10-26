package com.example.rustorescreen.presentation.ui

import androidx.compose.foundation.gestures.scrollable
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
import com.example.rustorescreen.domain.domainModel.AppDetails
import com.example.rustorescreen.presentation.viewModel.AppDetailsState

@Composable
fun AppDetailsContent(
    content: AppDetailsState.Content,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onInstallClick: () -> Unit,
    onDescriptionExpandClick: () -> Unit,
    onDeveloperInfoClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val appDetails: AppDetails = content.appDetails
    val descriptionExpanded: Boolean = content.descriptionExpanded

    val scrollState = rememberScrollState()

    Column(modifier = modifier.verticalScroll(scrollState)) {
        Toolbar(
            onBackClick = onBackClick,
            onShareClick = onShareClick,
        )
        Spacer(modifier = Modifier.height(4.dp))

        AppDetailsHeader(
            appDetails = appDetails,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        Spacer(Modifier.height(4.dp))

        InstallButton(
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