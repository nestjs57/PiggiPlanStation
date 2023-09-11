package com.arnoract.piggiplanstation.ui.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.arnoract.piggiplanstation.base.recyclerview.ItemViewHolder
import com.arnoract.piggiplanstation.ui.main.model.UiSeeMore
import com.arnoract.piggiplanstation.ui.main.model.UiSeeMoreDiffCallback
import com.arnoract.piggiplanstation.ui.main.viewholder.SeeMoreViewHolder

class SeeMoreAdapter(private val listener: SeeMoreAdapterListener) :
    ListAdapter<UiSeeMore, ItemViewHolder<UiSeeMore>>(
        UiSeeMoreDiffCallback()
    ) {

    interface SeeMoreAdapterListener {
        fun onClickedSeeMore()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<UiSeeMore> {
        return SeeMoreViewHolder.create(parent, listener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder<UiSeeMore>, position: Int) {
        holder.fillData(getItem(position), position)
    }
}