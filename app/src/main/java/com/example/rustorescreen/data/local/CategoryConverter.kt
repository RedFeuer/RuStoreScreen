package com.example.rustorescreen.data.local

import androidx.room.TypeConverter
import com.example.rustorescreen.domain.domainModel.AppCategory

class CategoryConverter {
    @TypeConverter
    fun fromCategory(category: AppCategory): String {
        return category.name
    }

    // возврат AppCategory по названию категории
    // valueOf смотрит ТОЧНО соотвествие(регистр влияет)
    // выбрасывает ошибки
    @TypeConverter
    fun toCategory(categoryName: String): AppCategory {
        return AppCategory.valueOf(categoryName)
    }
}