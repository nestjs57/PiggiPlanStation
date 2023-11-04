package com.arnoract.piggiplanstation.domain.model.main

data class RouteStation(
    val id: String,
    val nameTh: String,
    val nameEn: String,
    val type: Int,
    val typeName: String,
    var connections: List<RouteConnection> = mutableListOf()
) {
    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RouteStation

        if (id != other.id) return false
        if (connections != other.connections) return false

        return true
    }
}
