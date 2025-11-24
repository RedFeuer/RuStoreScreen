package com.example.rustorescreen.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// миграция из версии 2 в 3: добавляем колонку installStatus
val MIGRATION_2_3 = object:Migration(startVersion = 2, endVersion = 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE app_details" +
                " ADD COLUMN installStatus" +
                " TEXT NOT NULL DEFAULT 'Idle'")
    }
}