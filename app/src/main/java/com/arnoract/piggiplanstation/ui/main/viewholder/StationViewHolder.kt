package com.arnoract.piggiplanstation.ui.main.viewholder

import android.view.View
import android.view.ViewGroup
import com.arnoract.piggiplanstation.R
import com.arnoract.piggiplanstation.base.inflater
import com.arnoract.piggiplanstation.base.recyclerview.ItemViewHolder
import com.arnoract.piggiplanstation.databinding.ViewItemStationBinding
import com.arnoract.piggiplanstation.ui.main.model.UiStation

class StationViewHolder(
    private val binding: ViewItemStationBinding,
) : ItemViewHolder<UiStation>(binding.root) {

    companion object {
        fun create(
            parent: ViewGroup,
        ): ItemViewHolder<UiStation> {
            return StationViewHolder(
                ViewItemStationBinding.inflate(
                    parent.inflater(),
                    parent,
                    false
                )
            )
        }
    }

    override fun fillData(data: UiStation?, position: Int) {
        binding.tvStationNameTh.text = data?.name_th
        binding.tvStationNameEn.text = data?.name_en
        binding.tvDistance.text = data?.distanceStr
        binding.tvStationCode.text = data?.id

        binding.tvDistance.visibility =
            if (data?.isShowDistance == true) View.VISIBLE else View.INVISIBLE
        when (data?.type) {
            1 -> {
                binding.imgLogo.setImageResource(R.drawable.icon_bts)
                binding.layoutStation.setBackgroundResource(R.drawable.ic_bg_bts_sukhumvit)
                binding.tvStation.text = "BTS"
                binding.layoutStationCode.setBackgroundResource(R.drawable.ic_bg_bts_sukhumvit_stroke)
                binding.tvStationCode.setTextColor(context.getColor(R.color.green_sukhumvit))
            }
            2 -> {
                binding.imgLogo.setImageResource(R.drawable.icon_bts)
                binding.layoutStation.setBackgroundResource(R.drawable.ic_bg_bts_srilom)
                binding.tvStation.text = "BTS"
                binding.layoutStationCode.setBackgroundResource(R.drawable.ic_bg_bts_srilom_stroke)
                binding.tvStationCode.setTextColor(context.getColor(R.color.green_silom))
            }
            3 -> {
                binding.imgLogo.setImageResource(R.drawable.icon_mrt)
                binding.layoutStation.setBackgroundResource(R.drawable.ic_bg_mrt_blue_line)
                binding.tvStation.text = "MRT"
                binding.layoutStationCode.setBackgroundResource(R.drawable.ic_bg_mrt_blue_line_stroke)
                binding.tvStationCode.setTextColor(context.getColor(R.color.blue_mrt))
            }
            4 -> {
                binding.imgLogo.setImageResource(R.drawable.icon_mrt)
                binding.layoutStation.setBackgroundResource(R.drawable.ic_bg_mrt_purple_line)
                binding.tvStation.text = "MRT"
                binding.layoutStationCode.setBackgroundResource(R.drawable.ic_bg_mrt_purple_line_stroke)
                binding.tvStationCode.setTextColor(context.getColor(R.color.purple_mrt))
            }
            5 -> {
                binding.imgLogo.setImageResource(R.drawable.icon_apl)
                binding.layoutStation.setBackgroundResource(R.drawable.ic_bg_apl)
                binding.tvStation.text = "ARL"
                binding.layoutStationCode.setBackgroundResource(R.drawable.ic_bg_apl_stroke)
                binding.tvStationCode.setTextColor(context.getColor(R.color.pink_apl))
            }
            6 -> {
                binding.imgLogo.setImageResource(R.drawable.icon_bts)
                binding.layoutStation.setBackgroundResource(R.drawable.ic_bg_bts_gold)
                binding.tvStation.text = "BTS-G"
                binding.layoutStationCode.setBackgroundResource(R.drawable.ic_bg_bts_gold_stroke)
                binding.tvStationCode.setTextColor(context.getColor(R.color.gold_bts))
            }
            7 -> {
                binding.imgLogo.setImageResource(R.drawable.icon_srt)
                binding.layoutStation.setBackgroundResource(R.drawable.ic_bg_srtet_red)
                binding.tvStation.text = "SRTET"
                binding.layoutStationCode.setBackgroundResource(R.drawable.ic_bg_srtet_red_stroke)
                binding.tvStationCode.setTextColor(context.getColor(R.color.red_srt))
            }
            8 -> {
                binding.imgLogo.setImageResource(R.drawable.icon_srt)
                binding.layoutStation.setBackgroundResource(R.drawable.ic_bg_srtet_red_ligth)
                binding.tvStation.text = "SRTET"
                binding.layoutStationCode.setBackgroundResource(R.drawable.ic_bg_srtet_red_ligth_stroke)
                binding.tvStationCode.setTextColor(context.getColor(R.color.red_light_srt))
            }
        }
    }
}