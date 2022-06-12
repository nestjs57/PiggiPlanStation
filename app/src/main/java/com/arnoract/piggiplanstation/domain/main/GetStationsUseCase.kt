package com.arnoract.piggiplanstation.domain.main

import com.arnoract.piggiplanstation.core.UseCase
import com.arnoract.piggiplanstation.domain.model.main.Station

class GetStationsUseCase(
    private val stationRepository: StationRepository
) : UseCase<Unit, MutableList<Station>>() {

    override suspend fun execute(parameters: Unit): MutableList<Station> {
        return stationRepository.getStations()
    }
}