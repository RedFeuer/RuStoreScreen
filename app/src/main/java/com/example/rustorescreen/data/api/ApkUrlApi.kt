package com.example.rustorescreen.data.api

import kotlinx.coroutines.delay

class ApkUrlApi {
    suspend fun lookingForApk() {
        delay(300L)
    }

    suspend fun preparingApk() {
        delay(300L)
    }

    suspend fun getApk() {
        /* типо ходим в сеть и получаем URL для загрузки .apk */
        delay(3000L)
    }

    suspend fun cancelApk() {
        delay(200L)
    }

    suspend fun deleteApi() {
        delay(3000L)
    }
}