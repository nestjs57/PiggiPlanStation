package com.arnoract.piggiplanstation.ui.main.mapper

import android.location.Location
import com.arnoract.piggiplanstation.core.Mapper
import com.arnoract.piggiplanstation.domain.model.main.Station
import com.arnoract.piggiplanstation.ui.main.model.UiStation
import com.arnoract.piggiplanstation.ui.main.model.UiType
import com.arnoract.piggiplanstation.util.FormatUtils.formatNumberWithDecimal
import com.arnoract.piggiplanstation.util.FormatUtils.formatNumberWithOrWithOutDecimal
import com.arnoract.piggiplanstation.util.getDistanceMeter

class StationToUiStationMapper(private val lat: Double, private val long: Double) :
    Mapper<Station, UiStation> {
    override fun map(from: Station): UiStation {
        val locationA = Location("location_a").apply {
            latitude = from.lat.toDouble()
            longitude = from.long.toDouble()
        }
        val locationB = Location("location_b").apply {
            latitude = lat
            longitude = long
        }
        val distance = getDistanceMeter(locationA, locationB)
        val distanceStr = if (distance >= 1000.0
        ) "${
            formatNumberWithOrWithOutDecimal(
                distance.div(1000).toDouble()
            )
        } km" else "${formatNumberWithDecimal(distance.toDouble())} m"

        return UiStation(
            id = from.id,
            name_th = from.name_th,
            name_en = from.name_en,
            type = uiTypeMapper(from),
            type_name = from.type_name,
            have_parking = from.have_parking,
            distance = distance.toDouble(),
            distanceStr = distanceStr,
        )
    }

    private fun uiTypeMapper(from: Station): UiType {
        return when (from.type) {
            1 -> UiType.BTS_SKW
            2 -> UiType.BTS_SL
            3 -> UiType.MRT_BLUE
            4 -> UiType.MRT_PURPLE
            5 -> UiType.APL
            6 -> UiType.BTS_G
            7 -> UiType.RED_NORMAL
            8 -> UiType.RED_WEAK
            9 -> UiType.MRT_YELLOW
            else -> UiType.RED_WEAK
        }
    }
}