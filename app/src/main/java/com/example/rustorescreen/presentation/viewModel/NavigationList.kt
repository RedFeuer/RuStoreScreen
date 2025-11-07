package com.example.rustorescreen.presentation.viewModel

import android.net.Uri
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
import com.example.rustorescreen.presentation.ui.AppDetailsScreen
import com.example.rustorescreen.presentation.ui.AppListScreen

/* граф навигации в приложении */
/**
 * Основной корневой composable, содержащий NavHost и AppBar(Заголовок приложения и элементы управления).
 *
 * Поведение:
 * - Создаёт `NavController` через `rememberNavController`.
 * - Оборачивает содержание в `Scaffold` с `CenterAlignedTopAppBar`.
 * - Настраивает `NavHost` с двумя маршрутами:
 *   - `list` — экран списка приложений (`AppListScreen`). При клике на элемент
 *     вызывается `nav.navigate("details/${Uri.encode(appId)}")`, где `Uri.encode`
 *     безопасно кодирует пробелы, слэши и кириллицу в escape-последовательности.
 *   - `details/{appId}` — экран конкретного приложения (`AppDetailsScreen`). Для этого
 *     экрана аргумент `appId` объявлен как `NavType.StringType`. ViewModel для экрана
 *     создаётся через `hiltViewModel(viewModelStoreOwner = backStackEntry)`, чтобы
 *     scope ViewModel был привязан к конкретному элементу back stack и корректно
 *     очищался при удалении этого backStackEntry.
 *
 * Параметры/замечания:
 * - Используется экспериментальный Material3 API, поэтому функция помечена `@OptIn`.
 * - Обработчик `onBack` в экране конкретного приложения (кнопка "Назад") вызывает
 * `nav.popBackStack()` — возврат на список приложений.
 */
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
                        /*Uri.encode декодирует пробелы, / и кириллицу в #HH, чтобы
                        * строка безопасно вошла в путь, чтобы не сломать граф
                        * навигации nav.navigate*/
                        nav.navigate("details/${Uri.encode(appId)}") // navigate to details screen
                    }
                )
            }
            composable( // экран конкретного приложения
                route = "details/{appId}", // путь к экрану конкретного приложения
                arguments = listOf(navArgument("appId") { type = NavType.StringType }), // appId is String
            ) { backStackEntry ->
                /* получаем ViewModel, привязанную к конкретному backStackEntry
                   Делаем hiltViewModel(viewModelStoreOwner = backStackEntry), чтобы scope ViewModel
                   соответствовал элементу back stack — тогда ViewModel будет жить пока активен этот
                    backStackEntry и корректно очищен после его удаления из навигации.*/
                val viewModel: AppDetailsViewModel = hiltViewModel(viewModelStoreOwner = backStackEntry)
                AppDetailsScreen(
                    app = null,
                    viewModel = viewModel,
                    /* pop, чтобы вернуться к экрану с списком приложений при нажатии клавиши "Назад" */
                    onBack = { nav.popBackStack() },
                )
            }
        }
    }
}