package com.example.rustorescreen.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.rustorescreen.R
import com.example.rustorescreen.domain.domainModel.AppCategory
import com.example.rustorescreen.domain.domainModel.AppDetails

/**
 * Заголовок экрана конкретного приложения.
 *
 * Отображает иконку приложения, категорию, имя разработчика, возрастной рейтинг и размер.
 *
 * @param appDetails объект [AppDetails] содержащий данные приложения для отображения.
 * @param modifier модификатор для настройки внешнего вида и расположения корневого [Row].
 */
@Composable
fun AppDetailsHeader(
    appDetails: AppDetails,
    onCategoryUpdateClick: (AppCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = appDetails.iconUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(128.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Spacer(Modifier.width(16.dp))
        Column {
            AppCategoryDropdown(
                selectedAppCategory = appDetails.category,
                onSelectedAppCategoryClick = onCategoryUpdateClick,
            )

//            Text(
//                text = getCategoryText(appDetails.category),
//                color = MaterialTheme.colorScheme.secondary,
//                fontSize = 12.sp,
//            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = appDetails.developer,
                fontSize = 12.sp,
            )
            Spacer(Modifier.height(4.dp))
            Row {
                Column(modifier = Modifier.width(IntrinsicSize.Max),) {
                    Text(
                        text = "${appDetails.ageRating}+",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(text = stringResource(R.string.app_details_age))
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(text = "${appDetails.size} MB")
                    Spacer(Modifier.height(2.dp))
                    Text(text = stringResource(R.string.app_details_size))
                }
            }
        }
    }
}

/**
 * Выпадающий список для выбора категории приложения.
 *
 * Компонент отображает текущее значение в `OutlinedTextField` (только для чтения)
 * и привязанный к нему выпадающий список категорий из `AppCategory.entries`.
 * Состояние открытия меню хранится через `remember`. При выборе категории
 * вызывается `onSelectedAppCategoryClick` и меню закрывается.
 *
 * @param selectedAppCategory текущая выбранная категория, используемая для отображения текста.
 * @param onSelectedAppCategoryClick лямбда (AppCategory) -> Unit, вызываемая при выборе новой категории.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppCategoryDropdown(
    selectedAppCategory: AppCategory,
    onSelectedAppCategoryClick: (AppCategory) -> Unit,
) {
    var expanded by remember {mutableStateOf(false)} // храним состояние
    val categories = AppCategory.entries

    ExposedDropdownMenuBox(
        expanded = expanded, // состояние выпадения/закрывания
        onExpandedChange = {expanded = !expanded}, // действие для сворачивания
    ) {
        /* текущая выбранная категория, от которой будет отрисовываться список */
        OutlinedTextField(
            value = getCategoryText(selectedAppCategory),
            onValueChange = {},
            readOnly = true,
            label = {Text(stringResource(R.string.category))},
            modifier = Modifier
                /* "якорь" для меню
                * говорит приложению, что этот OutlinedTextField является элементов,
                * к которому пристыковывается выпадающий список ExposedDropdownMenu */
                .menuAnchor(
                    type = MenuAnchorType.PrimaryNotEditable, // основной якорь, поля не редактируемы
                    enabled = true // включаем выпадение списка
                )
                .fillMaxWidth()
        )

        /* список категорий */
        ExposedDropdownMenu(
            expanded = expanded, // состояние выпадения/закрывания
            onDismissRequest = { expanded = false }, // при нажатии вне выпавшего списка - он закрается
        ) {
            /* выводим все категории */
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(getCategoryText(category)) },
                    onClick = { // действия при клике на конкретную категорию
                        expanded = false // при выборе категории - сворачиваем список
                        /* вызов лямбды (AppCategory) -> Unit
                        * это вызов функции updateAppCategory(newCategory: AppCategory) из ViewModel*/
                        onSelectedAppCategoryClick(category)
                    },
                )
            }
        }
    }
}


/**
 * Возвращает локализованную строку категории по значению [AppCategory].
 *
 * Используется для отображения текстовой метки категории над именем разработчика.
 *
 * @param category категория приложения.
 * @return строковое представление категории на русском языке.
 */
@Composable
private fun getCategoryText(category: AppCategory): String = when (category) {
    AppCategory.APP -> "Приложения"
    AppCategory.GAME -> "Игры"
    AppCategory.PRODUCTIVITY -> "Продуктивность"
    AppCategory.SOCIAL -> "Социальные сети"
    AppCategory.EDUCATION -> "Образование"
    AppCategory.ENTERTAINMENT -> "Развлечения"
    AppCategory.MUSIC -> "Музыка"
    AppCategory.PHOTOGRAPHY -> "Фотография"
    AppCategory.HEALTH -> "Здоровье"
    AppCategory.SPORTS -> "Спорт"
    AppCategory.NEWS -> "Новости"
    AppCategory.BOOKS -> "Книги"
    AppCategory.BUSINESS -> "Бизнес"
    AppCategory.FINANCE -> "Финансы"
    AppCategory.LIFESTYLE -> "Образ жизни"
    AppCategory.TRAVEL -> "Путешествия"
    AppCategory.MAPS -> "Карты"
    AppCategory.FOOD -> "Еда и напитки"
    AppCategory.SHOPPING -> "Покупки"
    AppCategory.UTILITIES -> "Утилиты"
    AppCategory.WEATHER -> "Погода"
}

//@Preview
//@Composable
//private fun Preview() {
//    val appDetails = AppDetails(
//        id = 10,
//        name = "Гильдия Героев: Экшен ММО РПГ",
//        developer = "VK Play",
//        category = AppCategory.GAME,
//        ageRating = 12,
//        size = 223.7f,
//        screenshotUrlList = listOf(
//            "https://static.rustore.ru/imgproxy/-y8kd-4B6MQ-1OKbAbnoAIMZAzvoMMG9dSiHMpFaTBc/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/dfd33017-e90d-4990-aa8c-6f159d546788.jpg@webp",
//            "https://static.rustore.ru/imgproxy/dZCvNtRKKFpzOmGlTxLszUPmwi661IhXynYZGsJQvLw/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/60ec4cbc-dcf6-4e69-aa6f-cc2da7de1af6.jpg@webp",
//            "https://static.rustore.ru/imgproxy/g5whSI1uNqaL2TUO7TFfM8M63vXpWXNCm2vlX4Ahvc4/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/c2dde8bc-c4ab-482a-80a5-2789149f598d.jpg@webp",
//            "https://static.rustore.ru/imgproxy/TjeurtC7BczOVJt74XhjGYuQnG1l4rx6zpDqyMb00GY/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/08318f76-7a9c-43aa-b4a7-1aa878d00861.jpg@webp",
//        ),
//        iconUrl = "https://static.rustore.ru/imgproxy/APsbtHxkVa4MZ0DXjnIkSwFQ_KVIcqHK9o3gHY6pvOQ/preset:web_app_icon_62/plain/https://static.rustore.ru/apk/393868735/content/ICON/3f605e3e-f5b3-434c-af4d-77bc5f38820e.png@webp",
//        description = "Легендарный рейд героев в Фэнтези РПГ. Станьте героем гильдии и зразите мастера подземелья!"
//
//    )
////    VkEducationTheme {
////        AppDetailsHeader(appDetails = appDetails, modifier = Modifier.fillMaxWidth())
////    }
//}