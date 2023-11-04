package com.arnoract.piggiplanstation.ui.main.dialog.model

import com.arnoract.piggiplanstation.domain.model.main.RouteStation
import com.arnoract.piggiplanstation.ui.main.model.UiStation
import java.io.Serializable

data class UiOverview(
    val locationToGo: String,
    val startStation: UiStation?,
    val distanceBetweenCurrentAndStartStation: String,
    val endStation: UiStation,
    val distanceBetweenLastStationAndDestinationLocation: String,
    val routes: List<RouteStation>,
) : Serializable
