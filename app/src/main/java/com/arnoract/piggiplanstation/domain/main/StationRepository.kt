package com.arnoract.piggiplanstation.domain.main

import com.arnoract.piggiplanstation.domain.model.main.Station

interface StationRepository {
    suspend fun getStations(): MutableList<Station>
}