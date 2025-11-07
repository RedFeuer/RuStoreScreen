package com.example.rustorescreen.util

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LoggerImpl @Inject constructor():Logger {
    private val tag = "RuStoreApp"

    override fun d(message: String) {
        Log.d(tag, message)
    }

    override fun e(message: String, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }
}