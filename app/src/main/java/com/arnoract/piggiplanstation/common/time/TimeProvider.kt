package com.arnoract.piggiplanstation.common.time

import java.util.*

interface TimeProvider {
    fun now(): Long
    fun getCurrentTimeZone(): TimeZone
}
