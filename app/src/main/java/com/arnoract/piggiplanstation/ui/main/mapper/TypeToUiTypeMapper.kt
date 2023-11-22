package com.arnoract.piggiplanstation.ui.main.mapper

import com.arnoract.piggiplanstation.core.Mapper
import com.arnoract.piggiplanstation.ui.main.model.UiType

object TypeToUiTypeMapper : Mapper<Int, UiType> {
    override fun map(from: Int): UiType {
        return when (from) {
            1 -> UiType.BTS_SKW
            2 -> UiType.BTS_SL
            3 -> UiType.MRT_BLUE
            4 -> UiType.MRT_PURPLE
            5 -> UiType.APL
            6 -> UiType.BTS_G
            7 -> UiType.RED_NORMAL
            8 -> UiType.RED_WEAK
            9 -> UiType.MRT_YELLOW
            10 -> UiType.MRT_PINK
            else -> UiType.RED_WEAK
        }
    }
}