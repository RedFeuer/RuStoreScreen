package com.example.rustorescreen.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.rustorescreen.domain.domainModel.AppCategory
import com.example.rustorescreen.domain.domainModel.AppDetails

@Composable
fun AppDetailsHeader(
    appDetails: AppDetails,
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
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = getCategoryText(appDetails.category),
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 12.sp,
            )
        }
    }
}

@Composable
private fun getCategoryText(category: AppCategory): String = when (category) {
    AppCategory.APP -> "Приложения"
    AppCategory.GAME -> "Игры"
    AppCategory.PRODUCTIVITY -> "Продуктивность"
    AppCategory.SOCIAL -> "Социальные сети"
    AppCategory.EDUCATION -> "Образование"
    AppCategory.ENTERTAINMENT -> "Развлечения"
    AppCategory.MUSIC -> "Музыка"
    AppCategory.VIDEO -> "Видео"
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
    AppCategory.FOOD -> "Еда"
    AppCategory.SHOPPING -> "Покупки"
    AppCategory.UTILITIES -> "Утилиты"


}