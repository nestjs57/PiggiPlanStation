package com.arnoract.piggiplanstation.domain.model.main

import com.arnoract.piggiplanstation.MainActivity

data class RouteConnection(
    val to: RouteStation, val time: Int
) {
    override fun hashCode(): Int {
        return to.id.hashCode() * 31 + time
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RouteConnection

        if (to != other.to) return false
        if (time != other.time) return false

        return true
    }
}