package com.arnoract.piggiplanstation.domain.main

import android.util.Log
import com.arnoract.piggiplanstation.core.UseCase
import com.arnoract.piggiplanstation.domain.model.main.RouteConnection
import com.arnoract.piggiplanstation.domain.model.main.RouteStation
import com.arnoract.piggiplanstation.domain.model.main.Station
import com.google.gson.Gson

class FindShortestPathUseCase(
    private val stationRepository: StationRepository
) : UseCase<FindShortestPathUseCase.Params, List<RouteStation>>() {

    private data class ConnectionMap(
        val skwConnections: Map<String, List<String>>,
        val slConnections: Map<String, List<String>>,
        val mrtBlueConnections: Map<String, List<String>>,
        val mrtPurpleConnections: Map<String, List<String>>,
        val aplConnections: Map<String, List<String>>,
        val mrtYellowConnections: Map<String, List<String>>,
        val redNormalConnections: Map<String, List<String>>,
        val redWeakConnections: Map<String, List<String>>,
    )

    override suspend fun execute(parameters: Params): List<RouteStation> {

        fun createConnection(targetStation: RouteStation, cost: Int = 1) =
            RouteConnection(targetStation, cost)

        fun connectStations(stations: List<RouteStation>) {
            stations.forEachIndexed { index, station ->
                station.connections = when (index) {
                    0 -> listOf(createConnection(stations[1]))
                    stations.lastIndex -> listOf(createConnection(stations[index - 1]))
                    else -> listOf(
                        createConnection(stations[index - 1]), createConnection(stations[index + 1])
                    )
                }

                if (station.id == "BL01") {
                    station.connections += createConnection(stations.first { it.id == "BL32" })
                    station.connections += createConnection(stations.first { it.id == "BL33" })
                }
                if (station.id == "BL33") {
                    station.connections += createConnection(stations.first { it.id == "BL01" })
                    station.connections += createConnection(stations.first { it.id == "BL32" })
                }
                if (station.id == "BL32") {
                    station.connections += createConnection(stations.first { it.id == "BL01" })
                    station.connections += createConnection(stations.first { it.id == "BL33" })
                }
            }
        }

        fun updateConnections(
            stations: List<RouteStation>,
            connections: Map<String, List<String>>,
            dataSources: Map<String, List<RouteStation>>
        ) {
            stations.forEachIndexed { index, routeStation ->
                connections[routeStation.id]?.forEach { targetStationName ->
                    val targetStation =
                        dataSources.values.flatten().firstOrNull { it.id == targetStationName }
                    targetStation?.let {
                        stations[index].connections += RouteConnection(it, 1)
                    }
                }
            }
        }

        fun mapRouteStation(model: Station): RouteStation {
            return RouteStation(
                id = model.id, nameTh = model.name_th, type = model.type, typeName = model.type_name
            )
        }

        val allStation = stationRepository.getStations()

        val btsSkw = stationRepository.getBtsSkw().map { mapRouteStation(it) }
        connectStations(btsSkw)

        val btsSl = stationRepository.getBtsSl().map { mapRouteStation(it) }
        connectStations(btsSl)

        val mrtBlue = stationRepository.getMrtBlue().map { mapRouteStation(it) }
        connectStations(mrtBlue)

        val mrtPurple = stationRepository.getMrtPurple().map { mapRouteStation(it) }
        connectStations(mrtPurple)

        val apl = stationRepository.getApl().map { mapRouteStation(it) }
        connectStations(apl)

        val mrtYellow = stationRepository.getMrtYellow().map { mapRouteStation(it) }
        connectStations(mrtYellow)

        val redNormal = stationRepository.getRedNormal().map { mapRouteStation(it) }
        connectStations(redNormal)

        val redWeak = stationRepository.getRedWeak().map { mapRouteStation(it) }
        connectStations(redWeak)

        val systemRoutes =
            btsSkw + btsSl + mrtBlue + mrtPurple + apl + mrtYellow + redNormal + redWeak

        val connectionMaps = ConnectionMap(
            skwConnections = mapOf(
                "N09" to listOf("BL14"),
                "N08" to listOf("BL13"),
                "N02" to listOf("A7"),
                "E1" to listOf("CEN"),
                "E4" to listOf("BL22"),
                "E15" to listOf("YL23")
            ), slConnections = mapOf(
                "CEN" to listOf("E1"), "S2" to listOf("BL26"), "S12" to listOf("BL34")
            ), mrtBlueConnections = mapOf(
                "BL10" to listOf("PP16"),
                "BL13" to listOf("N08"),
                "BL14" to listOf("N09"),
                "BL15" to listOf("YL01"),
                "BL21" to listOf("A6"),
                "BL22" to listOf("E4"),
                "BL26" to listOf("S2"),
                "BL34" to listOf("S12"),
                "BL11" to listOf("RN01", "RW01")
            ), mrtPurpleConnections = mapOf(
                "PP15" to listOf("RW02"), "PP16" to listOf("BL10")
            ), aplConnections = mapOf(
                "A4" to listOf("YL11"), "A6" to listOf("BL21"), "A8" to listOf("N02")
            ), mrtYellowConnections = mapOf(
                "YL01" to listOf("BL15"), "YL11" to listOf("A4"), "YL23" to listOf("E15")
            ), redNormalConnections = mapOf(
                "RN01" to listOf("BL11")
            ), redWeakConnections = mapOf(
                "RW01" to listOf("BL11"), "RW02" to listOf("PP15")
            )
        )

        updateConnections(
            btsSkw, connectionMaps.skwConnections, mapOf(
                "BL14" to mrtBlue, "A7" to apl, "CEN" to btsSl, "YL23" to mrtYellow, "S2" to btsSl
            )
        )
        updateConnections(
            btsSl,
            connectionMaps.slConnections,
            mapOf("E1" to btsSkw, "BL26" to mrtBlue, "BL34" to mrtBlue)
        )
        updateConnections(
            mrtBlue, connectionMaps.mrtBlueConnections, mapOf(
                "PP16" to mrtPurple,
                "N08" to btsSkw,
                "N09" to btsSkw,
                "YL01" to mrtYellow,
                "A6" to apl,
                "E4" to btsSkw,
                "S2" to btsSl,
                "S12" to btsSl,
                "RN01" to redNormal,
                "RW01" to redWeak
            )
        )
        updateConnections(
            mrtPurple,
            connectionMaps.mrtPurpleConnections,
            mapOf("RW02" to redWeak, "BL10" to mrtBlue)
        )

        updateConnections(
            apl,
            connectionMaps.aplConnections,
            mapOf("YL11" to mrtYellow, "BL21" to mrtBlue, "N02" to btsSkw)
        )

        updateConnections(
            mrtYellow,
            connectionMaps.mrtYellowConnections,
            mapOf("BL15" to mrtBlue, "A4" to apl, "E15" to btsSkw)
        )

        updateConnections(
            redNormal, connectionMaps.redNormalConnections, mapOf("BL11" to mrtBlue)
        )

        updateConnections(
            redWeak,
            connectionMaps.redWeakConnections,
            mapOf("BL11" to mrtBlue, "PP15" to mrtPurple)
        )

        val startStation = systemRoutes.first { it.id == parameters.startStationId }
        val endStation = systemRoutes.first { it.id == parameters.endStationId }

        return findShortestPath(startStation, endStation)
    }

    data class Params(val startStationId: String, val endStationId: String)

    private fun findShortestPath(start: RouteStation, end: RouteStation): List<RouteStation> {
        val shortestPaths = mutableMapOf<RouteStation, Pair<Int, RouteStation?>>()
        val unvisited = mutableListOf<RouteStation>()

        fun findLowestCostStation() = unvisited.minByOrNull { shortestPaths[it]!!.first }

        unvisited.add(start)
        shortestPaths[start] = Pair(0, null)

        while (unvisited.isNotEmpty()) {
            val currentStation = findLowestCostStation() ?: break
            unvisited.remove(currentStation)

            for (connection in currentStation.connections) {
                if (connection.to !in shortestPaths.keys) {
                    unvisited.add(connection.to)
                    shortestPaths[connection.to] = Pair(Int.MAX_VALUE, null)
                }

                val newDistance = shortestPaths[currentStation]!!.first + connection.time
                if (newDistance < shortestPaths[connection.to]!!.first) {
                    shortestPaths[connection.to] = Pair(newDistance, currentStation)
                }
            }
        }

        val path = mutableListOf<RouteStation>()
        var step = end
        while (step != start) {
            path.add(0, step)
            step = shortestPaths[step]?.second ?: throw IllegalArgumentException("Path not found")
        }
        path.add(0, start)
        return path
    }
}