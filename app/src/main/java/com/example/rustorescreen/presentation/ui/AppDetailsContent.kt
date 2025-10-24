package com.example.rustorescreen.presentation.ui

import android.widget.Toolbar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
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

    Column(modifier) {
        Toolbar(
            onBackClick = onBackClick,
            onShareClick = onShareClick,
        )
        Spacer(modifier = Modifier.height(8.dp))
        AppDetailsHeader(
            appDetails = appDetails,
            modifier = Modifier.padding(horizontal = 16.dp),
        )

    }
}