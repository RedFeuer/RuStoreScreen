package com.example.rustorescreen.data.api

import kotlinx.coroutines.delay

class ApkUrlApi {
    suspend fun getApk() {
        /* типо ходим в сеть и получаем URL для загрузки .apk */
        delay(3000L)
    }
}