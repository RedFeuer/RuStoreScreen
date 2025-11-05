package com.example.rustorescreen.presentation.viewModel

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rustorescreen.data.repositoryImpl.AppListRepositoryImpl
import com.example.rustorescreen.domain.domainModel.AppDetails
import com.example.rustorescreen.domain.repositoryInterface.AppListRepository
import com.example.rustorescreen.domain.useCase.GetAppListUseCase
import com.example.rustorescreen.presentation.ui.AppDetailsScreen
import com.example.rustorescreen.presentation.ui.AppListScreen

/* граф навигации в приложении */
@OptIn(ExperimentalMaterial3Api::class) // using Material3 experimental API
@Composable
internal fun AppRoot() {
    val nav = rememberNavController()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("RuStore") })
        }
    ) { inner->
        NavHost(
            navController = nav,
            startDestination = "list",
            modifier = Modifier.padding(inner)
        ) {
            /* экранчики и навигация к ним */
            composable(route = "list") { // экран списка приложений
                AppListScreen( // list of apps
                    onAppClick = { appId ->
                        nav.navigate("details/$appId") // navigate to details screen
                    }
                )
            }
            composable( // экран конкретного приложения
                route = "details/{appId}", // details screen with appId argument
                arguments = listOf(navArgument("appId") { type = NavType.IntType }), // appId is Int
            ) { backStackEntry ->
                val viewModel: AppDetailsViewModel = hiltViewModel(viewModelStoreOwner = backStackEntry)
                AppDetailsScreen(
                    app = null,
                    viewModel = viewModel,
                    onBack = { nav.popBackStack() }, // details of app with back action
                )
            }
        }
    }
}