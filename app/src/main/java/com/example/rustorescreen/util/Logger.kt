package com.example.rustorescreen.util

interface Logger {
    fun d(message: String)
    fun e(message: String, throwable: Throwable? = null)
}