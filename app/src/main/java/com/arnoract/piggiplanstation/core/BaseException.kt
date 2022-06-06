package com.arnoract.piggiplanstation.core

/**
 * Base class for all exceptions in the domain layer.
 */
open class BaseException : RuntimeException {
    constructor() : super()
    constructor(cause: String) : super(cause)
}
