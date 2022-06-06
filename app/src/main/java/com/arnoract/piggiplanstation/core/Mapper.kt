package com.arnoract.piggiplanstation.core

interface Mapper<in From, out To> {
    fun map(from: From): To
}