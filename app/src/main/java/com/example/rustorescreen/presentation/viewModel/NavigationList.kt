package com.example.rustorescreen.presentation.viewModel

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rustorescreen.data.repositoryImpl.AppRepositoryImpl
import com.example.rustorescreen.domain.domainModel.App
import com.example.rustorescreen.domain.repositoryInterface.AppRepository
import com.example.rustorescreen.domain.useCase.GetAppUseCase
import com.example.rustorescreen.presentation.ui.AppDetailsScreen
import com.example.rustorescreen.presentation.ui.AppListScreen

@OptIn(ExperimentalMaterial3Api::class) // using Material3 experimental API
@Composable
internal fun AppRoot() {
    val nav = rememberNavController()
    val repository: AppRepository = AppRepositoryImpl() // create Repository instance
    val getAppUseCase: GetAppUseCase
    try {
        getAppUseCase = GetAppUseCase(repository) // create UseCase instance
    }
    catch (e: IllegalArgumentException) {
        // Handle the exception (e.g., log it or show an error message)
        println("Error initializing GetAppUseCase: ${e.message}")
    }
    val apps: List<App> = getAppUseCase() // get all Apps from Repository

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
            composable("list") {
                AppListScreen( // list of apps
                    apps = apps, // get all apps from repository
                    onAppClick = { appId ->
                        nav.navigate("details/$appId") // navigate to details screen
                    }
                )
            }
            composable(
                route = "details/{appId}", // details screen with appId argument
                arguments = listOf(navArgument("appId") { type = NavType.IntType }), // appId is Int
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("appId") ?: return@composable // if no id, return
                val app = apps.find { it.id == id } // find app(domain) by id
                if (app != null) {
                    AppDetailsScreen(
                        app = app,
                        onBack = { nav.popBackStack() }) // details of app with back action
                }
            }
        }
    }
}