package com.example.rustorescreen.data.api

import com.example.rustorescreen.domain.domainModel.AppCategory
import com.example.rustorescreen.data.dto.AppDto

/* hardcoded */
class AppListAPI {
    private val items = listOf(
        AppDto(
            id = 1,
            title = "Сбербанк Онлайн",
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
        AppDto(
            id = 2,
            title = "Яндекс Браузер",
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
        AppDto(
            id = 3,
            title = "Почта Mail.ru",
            developer = "Mail.ru",
            description = "Почтовый клиент для любых ящиков",
            category = AppCategory.APP,
            ageRating = 0,
            size = 18.0f,
            iconUrl = "https://example.com/icons/mailru.png",
            screenshotUrlList = listOf("https://example.com/screens/mailru_1.png")
        ),
        AppDto(
            id = 4,
            title = "Яндекс Навигатор",
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
        AppDto(
            id = 5,
            title = "Мой МТС",
            developer = "МТС",
            description = "Мой МТС - центр экосистемы МТС",
            category = AppCategory.APP,
            ageRating = 0,
            size = 32.1f,
            iconUrl = "https://example.com/icons/mts.png",
            screenshotUrlList = listOf("https://example.com/screens/mts_1.png")
        ),
        AppDto(
            id = 6,
            title = "Яндекс с Алисой",
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
        AppDto(
            id = 7,
            title = "Гильдия Героев: Экшен ММО РПГ",
            developer = "VK Play",
            category = AppCategory.GAME,
            ageRating = 12,
            size = 223.7f,
            screenshotUrlList = listOf(
                "https://static.rustore.ru/imgproxy/-y8kd-4B6MQ-1OKbAbnoAIMZAzvoMMG9dSiHMpFaTBc/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/dfd33017-e90d-4990-aa8c-6f159d546788.jpg@webp",
                "https://static.rustore.ru/imgproxy/dZCvNtRKKFpzOmGlTxLszUPmwi661IhXynYZGsJQvLw/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/60ec4cbc-dcf6-4e69-aa6f-cc2da7de1af6.jpg@webp",
                "https://static.rustore.ru/imgproxy/g5whSI1uNqaL2TUO7TFfM8M63vXpWXNCm2vlX4Ahvc4/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/c2dde8bc-c4ab-482a-80a5-2789149f598d.jpg@webp",
                "https://static.rustore.ru/imgproxy/TjeurtC7BczOVJt74XhjGYuQnG1l4rx6zpDqyMb00GY/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/08318f76-7a9c-43aa-b4a7-1aa878d00861.jpg@webp",
            ),
            iconUrl = "https://static.rustore.ru/imgproxy/APsbtHxkVa4MZ0DXjnIkSwFQ_KVIcqHK9o3gHY6pvOQ/preset:web_app_icon_62/plain/https://static.rustore.ru/apk/393868735/content/ICON/3f605e3e-f5b3-434c-af4d-77bc5f38820e.png@webp",
            description = "Легендарный рейд героев в Фэнтези РПГ. Станьте героем гильдии и cразите мастера подземелья!"
        ),
    )

    fun getAppList(): List<AppDto> = items
    fun getById(id: Int): AppDto? = items.find { it.id == id }
}