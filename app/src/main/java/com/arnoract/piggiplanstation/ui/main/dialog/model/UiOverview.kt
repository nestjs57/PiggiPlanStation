package com.arnoract.piggiplanstation.ui.main.dialog.model

import com.arnoract.piggiplanstation.domain.model.main.RouteStation
import com.arnoract.piggiplanstation.ui.main.model.UiStation

data class UiOverview(
    val locationToGo : String,
    val startStation: UiStation?,
    val distanceBetweenCurrentAndStartStation: String,
    val endStation: UiStation,
    val distanceBetweenLastStationAndDestinationLocation: String,
    val routes: List<RouteStation>,
)
