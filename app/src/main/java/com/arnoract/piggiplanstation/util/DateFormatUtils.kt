package com.arnoract.piggiplanstation.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateFormatUtils {
    fun formatDateMonthYearSlash(
        date: Date,
        timeZone: TimeZone = TimeZone.getDefault()
    ): String {
        val slashDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply {
            this.timeZone = timeZone
        }
        return slashDateFormat.format(date)
    }

    fun formatDateMonthYearSlashWithTime(
        date: Date,
        timeZone: TimeZone = TimeZone.getDefault()
    ): String {
        val slashDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).apply {
            this.timeZone = timeZone
        }
        return slashDateFormat.format(date)
    }

    fun formatDateMonthYearSlashWithTimestamp(
        date: Date,
        timeZone: TimeZone
    ): String {
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).apply {
            this.timeZone = timeZone
        }
        return format.format(date)
    }

    fun formatTimestamp(
        date: Date,
        timeZone: TimeZone
    ): String {
        val format = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).apply {
            this.timeZone = timeZone
        }
        return format.format(date)
    }

    fun formatTime(
        date: Date,
        timeZone: TimeZone
    ): String {
        val format = SimpleDateFormat("HH:mm", Locale.getDefault()).apply {
            this.timeZone = timeZone
        }
        return format.format(date)
    }

    fun formatYearMonthDateSlash(date: Date): String {
        val slashDateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        return slashDateFormat.format(date)
    }

    fun formatYearMonthDateWithTimeMillisecond(date: Date): String {
        val format = SimpleDateFormat("yyyyMMddHHmmssSSSSSS", Locale.getDefault())
        return format.format(date)
    }

    fun formatYearMonthDateWithTime(
        date: Date,
        timeZone: TimeZone
    ): String {
        val format = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).apply {
            this.timeZone = timeZone
        }
        return format.format(date)
    }

    fun formatYearMonthDateWithHyphenTimestamp(
        date: Date,
        timeZone: TimeZone
    ): String {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).apply {
            this.timeZone = timeZone
        }
        return format.format(date)
    }

    fun formatYearMonthDateWithHyphen(
        date: Date,
        timeZone: TimeZone
    ): String {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
            this.timeZone = timeZone
        }
        return format.format(date)
    }

    fun formatDateMonthWithSpace(
        date: Date,
        timeZone: TimeZone
    ): String {
        val format = SimpleDateFormat("d MMM", Locale.getDefault()).apply {
            this.timeZone = timeZone
        }
        return format.format(date)
    }

    fun formatDateMonthYearWithSpace(
        date: Date,
        timeZone: TimeZone
    ): String {
        val format = SimpleDateFormat("d MMM yyyy", Locale.getDefault()).apply {
            this.timeZone = timeZone
        }
        return format.format(date)
    }

    fun formatDateMonthYearSlashAndTimeWithDash(
        date: Date,
        timeZone: TimeZone = TimeZone.getDefault()
    ): String {
        val slashDateFormat = SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault()).apply {
            this.timeZone = timeZone
        }
        return slashDateFormat.format(date)
    }

    fun toDateFormatMonthYearTimeSlashWithDash(
        date: String,
        timeZone: TimeZone = TimeZone.getDefault()
    ): Date? {
        val timeFormat = SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault()).apply {
            this.timeZone = timeZone
        }

        return try {
            timeFormat.parse(date)
        } catch (e: ParseException) {
            null
        }
    }

    fun toDateFormatMonthYearTimeWithHyphen(
        date: String,
        timeZone: TimeZone = TimeZone.getDefault()
    ): Date? {
        val timeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).apply {
            this.timeZone = timeZone
        }

        return try {
            timeFormat.parse(date)
        } catch (e: ParseException) {
            null
        }
    }

    fun formatShortDateTime(
        date: Date,
        now: Date,
        timeZone: TimeZone
    ): String {
        val calendarNow = Calendar.getInstance(timeZone).apply {
            timeInMillis = now.time
        }
        val calendarDate = Calendar.getInstance(timeZone).apply {
            timeInMillis = date.time
        }
        return if (calendarNow.get(Calendar.DAY_OF_YEAR) == calendarDate.get(Calendar.DAY_OF_YEAR)) {
            formatTime(date, timeZone)
        } else {
            formatDateMonthYearSlashWithTime(date, timeZone)
        }
    }
}
