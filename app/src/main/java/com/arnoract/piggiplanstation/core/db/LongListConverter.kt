package com.arnoract.piggiplanstation.core.db

import androidx.room.TypeConverter

class LongListConverter {
    @TypeConverter
    fun fromString(value: String?): List<Long>? {
        if (value == null) return null
        if (value.isBlank()) return emptyList()
        return value.split(",").map { it.toLong() }
    }

    @TypeConverter
    fun fromList(list: List<Long>?): String? {
        return list?.joinToString(",")
    }
}