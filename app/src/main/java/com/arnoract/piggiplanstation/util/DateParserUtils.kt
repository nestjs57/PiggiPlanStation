package com.arnoract.piggiplanstation.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeParseException
import java.util.*

object DateParserUtils {
    fun parseYearMonthDateWithHyphenTimestamp(
        date: String,
        timeZone: TimeZone
    ): Date? {
        val timeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).apply {
            this.timeZone = timeZone
        }
        return try {
            timeFormat.parse(date)
        } catch (e: ParseException) {
            null
        }
    }

    fun parseTime(time: String): LocalTime? {
        return try {
            LocalTime.parse(time)
        } catch (e: DateTimeParseException) {
            null
        }
    }
}
