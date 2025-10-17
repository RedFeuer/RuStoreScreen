package com.example.rustorescreen.data.database

import com.example.rustorescreen.domain.domainModel.AppCategory
import com.example.rustorescreen.domain.domainModel.AppItem

/* hardcoded */
object AppDatabase {
    private val items = listOf(
        AppItem(
            id = 1,
            name = "Сбербанк Онлайн",
            developer = "Сбербанк",
            description = "Больше чем банк",
            category = AppCategory.APP,
            ageRating = 3,
            size = 28.6f,
            iconUrl = "https://example.com/icons/sber.png",
            screenshotUrlList = listOf(
                "https://example.com/screens/sber_1.png",
                "https://example.com/screens/sber_2.png"
            )
        ),
        AppItem(
            id = 2,
            name = "Яндекс Браузер",
            developer = "Яндекс",
            description = "Быстрый и безопасный браузер",
            category = AppCategory.APP,
            ageRating = 0,
            size = 45.2f,
            iconUrl = "https://example.com/icons/yandex_browser.png",
            screenshotUrlList = listOf(
                "https://example.com/screens/yandex_1.png",
                "https://example.com/screens/yandex_2.png"
            )
        ),
        AppItem(
            id = 3,
            name = "Почта Mail.ru",
            developer = "Mail.ru",
            description = "Почтовый клиент для любых ящиков",
            category = AppCategory.APP,
            ageRating = 0,
            size = 18.0f,
            iconUrl = "https://example.com/icons/mailru.png",
            screenshotUrlList = listOf("https://example.com/screens/mailru_1.png")
        ),
        AppItem(
            id = 4,
            name = "Яндекс Навигатор",
            developer = "Яндекс",
            description = "Парковки и заправки - по пути",
            category = AppCategory.APP,
            ageRating = 0,
            size = 60.4f,
            iconUrl = "https://example.com/icons/yandex_nav.png",
            screenshotUrlList = listOf(
                "https://example.com/screens/nav_1.png",
                "https://example.com/screens/nav_2.png"
            )
        ),
        AppItem(
            id = 5,
            name = "Мой МТС",
            developer = "МТС",
            description = "Мой МТС - центр экосистемы МТС",
            category = AppCategory.APP,
            ageRating = 0,
            size = 32.1f,
            iconUrl = "https://example.com/icons/mts.png",
            screenshotUrlList = listOf("https://example.com/screens/mts_1.png")
        ),
        AppItem(
            id = 6,
            name = "Яндекс с Алисой",
            developer = "Яндекс",
            description = "Яндекс - всегда под рукой",
            category = AppCategory.APP,
            ageRating = 0,
            size = 50.0f,
            iconUrl = "https://example.com/icons/yandex_alice.png",
            screenshotUrlList = listOf(
                "https://example.com/screens/alice_1.png",
                "https://example.com/screens/alice_2.png"
            )
        ),
    )

    fun all(): List<AppItem> = items
    fun byId(id: Int): AppItem? = items.find { it.id == id }
}