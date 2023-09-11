package com.arnoract.piggiplanstation.ui.main.viewholder

import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.ViewGroup
import com.arnoract.piggiplanstation.base.inflater
import com.arnoract.piggiplanstation.base.recyclerview.ItemViewHolder
import com.arnoract.piggiplanstation.core.setDebounceOnClickListener
import com.arnoract.piggiplanstation.databinding.ViewItemSeeMoreBinding
import com.arnoract.piggiplanstation.databinding.ViewItemStationBinding
import com.arnoract.piggiplanstation.ui.main.adapter.SeeMoreAdapter
import com.arnoract.piggiplanstation.ui.main.model.UiSeeMore
import com.arnoract.piggiplanstation.ui.main.model.UiStation

class SeeMoreViewHolder(
    private val binding: ViewItemSeeMoreBinding,
    private val listener: SeeMoreAdapter.SeeMoreAdapterListener
) : ItemViewHolder<UiSeeMore>(binding.root) {

    companion object {
        fun create(
            parent: ViewGroup,
            listener: SeeMoreAdapter.SeeMoreAdapterListener,
        ): ItemViewHolder<UiSeeMore> {
            return SeeMoreViewHolder(
                ViewItemSeeMoreBinding.inflate(
                    parent.inflater(), parent, false
                ), listener
            )
        }
    }

    override fun fillData(data: UiSeeMore?, position: Int) {
        val content = SpannableString(data?.seeMoreLabel)
        content.setSpan(UnderlineSpan(), 0, data?.seeMoreLabel?.length ?: 0, 0)
        binding.tvSeeMore.text = content

        binding.tvSeeMore.setDebounceOnClickListener {
            listener.onClickedSeeMore()
        }
    }
}