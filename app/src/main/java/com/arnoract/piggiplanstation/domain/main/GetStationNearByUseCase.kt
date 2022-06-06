package com.arnoract.piggiplanstation.domain.main

import com.arnoract.piggiplanstation.core.UseCase
import com.arnoract.piggiplanstation.domain.model.main.Station

class GetStationNearByUseCase(
    private val stationRepository: StationRepository
) : UseCase<GetStationNearByUseCase.Params, MutableList<Station>>() {

    override suspend fun execute(parameters: Params): MutableList<Station> {
        return stationRepository.getStations()
    }

    data class Params(
        val lat: Double,
        val long: Double
    )
}