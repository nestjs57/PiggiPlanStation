package com.arnoract.piggiplanstation.ui.main.model

import androidx.recyclerview.widget.DiffUtil

data class UiStation(
    val id: String,
    val name_th: String,
    val name_en: String,
    val type: UiType,
    val type_name: String,
    val have_parking: Int,
    val distance: Double,
    val distanceStr: String,
    val isShowDistance: Boolean? = true ,
    val position : Int = 0
)

class UiStationDiffCallback : DiffUtil.ItemCallback<UiStation>() {
    override fun areItemsTheSame(
        oldItem: UiStation,
        newItem: UiStation,
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: UiStation,
        newItem: UiStation,
    ): Boolean {
        return oldItem == newItem
    }
}
