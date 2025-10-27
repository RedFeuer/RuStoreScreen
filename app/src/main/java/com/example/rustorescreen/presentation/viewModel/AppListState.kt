package com.example.rustorescreen.presentation.viewModel

import com.example.rustorescreen.domain.domainModel.AppDetails

sealed interface AppListState {
    data object Loading: AppListState
    data object Error: AppListState

    data class Content(
        val appList: List<AppDetails>,
        // TODO: add more fields if needed
    )
}