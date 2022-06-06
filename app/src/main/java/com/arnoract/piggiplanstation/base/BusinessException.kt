package com.arnoract.piggiplanstation.base

open class BusinessException : RuntimeException {
    constructor() : super()
    constructor(cause: String) : super(cause)
}