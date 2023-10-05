package com.arnoract.piggiplanstation.ui.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.arnoract.piggiplanstation.base.recyclerview.ItemViewHolder
import com.arnoract.piggiplanstation.ui.main.model.UiStation
import com.arnoract.piggiplanstation.ui.main.model.UiStationDiffCallback
import com.arnoract.piggiplanstation.ui.main.viewholder.StationViewHolder

class StationAdapter(
    private val listener: StationAdapterListener
) :
    ListAdapter<UiStation, ItemViewHolder<UiStation>>(
        UiStationDiffCallback()
    ) {

    interface StationAdapterListener {
        fun onClickStation(model : UiStation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<UiStation> {
        return StationViewHolder.create(parent, listener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder<UiStation>, position: Int) {
        holder.fillData(getItem(position), position)
    }
}