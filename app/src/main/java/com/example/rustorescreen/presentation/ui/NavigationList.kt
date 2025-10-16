package com.example.rustorescreen.presentation.ui

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
import com.example.rustorescreen.data.AppRepository

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
            composable("list") {
                AppListScreen( // list of apps
                    apps = AppRepository.all(), // get all apps from repository
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
                val app = AppRepository.byId(id) // get app by id
                if (app != null) {
                    AppDetailsScreen(
                        app = app,
                        onBack = { nav.popBackStack() }) // details of app with back action
                }
            }
        }
    }
}