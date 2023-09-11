package com.arnoract.piggiplanstation.ui.main.model

import androidx.recyclerview.widget.DiffUtil

data class UiSeeMore(
    val seeMoreLabel: String
)

class UiSeeMoreDiffCallback : DiffUtil.ItemCallback<UiSeeMore>() {
    override fun areItemsTheSame(
        oldItem: UiSeeMore,
        newItem: UiSeeMore,
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: UiSeeMore,
        newItem: UiSeeMore,
    ): Boolean {
        return oldItem == newItem
    }
}
