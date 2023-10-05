package com.arnoract.piggiplanstation.domain.main

import com.arnoract.piggiplanstation.domain.model.main.Station

interface StationRepository {
    suspend fun getStations(): MutableList<Station>
    suspend fun getBtsSkw(): MutableList<Station>
    suspend fun getBtsSl(): MutableList<Station>
    suspend fun getMrtBlue(): MutableList<Station>
    suspend fun getMrtPurple(): MutableList<Station>
    suspend fun getApl(): MutableList<Station>
    suspend fun getMrtYellow(): MutableList<Station>
    suspend fun getRedNormal(): MutableList<Station>
    suspend fun getRedWeak(): MutableList<Station>
}