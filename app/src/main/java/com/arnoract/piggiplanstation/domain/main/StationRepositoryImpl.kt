package com.arnoract.piggiplanstation.domain.main

import android.content.Context
import com.arnoract.piggiplanstation.domain.model.main.Station
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class StationRepositoryImpl(
    val context: Context
) : StationRepository {
    override suspend fun getStations(): MutableList<Station> {
        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("csvjson.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {

        }
        val stations: List<Station> = jsonString.toArrayClass(Station::class.java)
        return stations.toMutableList()
    }

    override suspend fun getBtsSkw(): MutableList<Station> {
        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("skw.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {

        }
        val stations: List<Station> = jsonString.toArrayClass(Station::class.java)
        return stations.toMutableList()
    }

    override suspend fun getBtsSl(): MutableList<Station> {
        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("sl.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {

        }
        val stations: List<Station> = jsonString.toArrayClass(Station::class.java)
        return stations.toMutableList()
    }

    override suspend fun getMrtBlue(): MutableList<Station> {
        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("blueline.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {

        }
        val stations: List<Station> = jsonString.toArrayClass(Station::class.java)
        return stations.toMutableList()
    }

    override suspend fun getMrtPurple(): MutableList<Station> {
        lateinit var jsonString: String
        try {
            jsonString =
                context.assets.open("purpleline.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {

        }
        val stations: List<Station> = jsonString.toArrayClass(Station::class.java)
        return stations.toMutableList()
    }

    override suspend fun getApl(): MutableList<Station> {
        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("apl.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {

        }
        val stations: List<Station> = jsonString.toArrayClass(Station::class.java)
        return stations.toMutableList()
    }

    override suspend fun getMrtYellow(): MutableList<Station> {
        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("yellow.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {

        }
        val stations: List<Station> = jsonString.toArrayClass(Station::class.java)
        return stations.toMutableList()
    }

    override suspend fun getRedNormal(): MutableList<Station> {
        lateinit var jsonString: String
        try {
            jsonString =
                context.assets.open("rednormal.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {

        }
        val stations: List<Station> = jsonString.toArrayClass(Station::class.java)
        return stations.toMutableList()
    }

    override suspend fun getRedWeak(): MutableList<Station> {
        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("redweak.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {

        }
        val stations: List<Station> = jsonString.toArrayClass(Station::class.java)
        return stations.toMutableList()
    }

    override suspend fun getMrtPink(): MutableList<Station> {
        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("pink.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {

        }
        val stations: List<Station> = jsonString.toArrayClass(Station::class.java)
        return stations.toMutableList()
    }

    private fun <T : Any?> String.toArrayClass(toClass: Class<Station>): ArrayList<T> {
        val gson = Gson()

        val type = object : TypeToken<ArrayList<Station>>() {}.type
        return gson.fromJson(this, type)
    }
}