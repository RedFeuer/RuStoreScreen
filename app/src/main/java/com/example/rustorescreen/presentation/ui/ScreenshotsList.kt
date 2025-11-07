package com.example.rustorescreen.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.rustorescreen.R

/**
 * Отображает заголовок и горизонтальный список скриншотов приложения.
 *
 * Компонент рендерит заголовок секции и пэйджер (`HorizontalPager`) с изображениями,
 * загружаемыми через Coil (`AsyncImage`). Каждый скриншот масштабируется до
 * соотношения сторон 16:9 и скругляется с радиусом 8.dp.
 *
 * @param screenshotUrlList список URL-строк с изображениями скриншотов.
 * @param contentPadding отступы, которые применяются к заголовку и к содержимому пэйджера.
 * @param modifier дополнительный [Modifier], применяемый к корневому контейнеру.
 */
@Composable
fun ScreenshotsList(
    screenshotUrlList: List<String>,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text (
            text = stringResource(R.string.app_details_screenshots),
            modifier = Modifier.padding(contentPadding),
        )
        Spacer(Modifier.height(8.dp))
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = contentPadding,
            pageSpacing = 8.dp,
            state = rememberPagerState { screenshotUrlList.size },
        ) { index ->
            AsyncImage(
                model = screenshotUrlList[index],
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(8.dp)),
            )
        }
    }
}