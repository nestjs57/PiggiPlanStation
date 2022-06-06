package com.arnoract.piggiplanstation.domain.model.main

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Station(
    val id: String,
    val name_th: String,
    val name_en: String,
    val type: Int,
    val type_name: String,
    val have_parking: Int,
    val lat: Float,
    val long: Float
)
