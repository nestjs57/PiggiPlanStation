package com.arnoract.piggiplanstation.ui.main.viewholder

import android.view.View
import android.view.ViewGroup
import com.arnoract.piggiplanstation.R
import com.arnoract.piggiplanstation.base.inflater
import com.arnoract.piggiplanstation.base.recyclerview.ItemViewHolder
import com.arnoract.piggiplanstation.core.setDebounceOnClickListener
import com.arnoract.piggiplanstation.databinding.ViewItemStationBinding
import com.arnoract.piggiplanstation.ui.main.adapter.StationAdapter
import com.arnoract.piggiplanstation.ui.main.model.UiStation
import com.arnoract.piggiplanstation.ui.main.model.UiType

class StationViewHolder(
    private val binding: ViewItemStationBinding,
    private val listener: StationAdapter.StationAdapterListener
) : ItemViewHolder<UiStation>(binding.root) {

    companion object {
        fun create(
            parent: ViewGroup,
            listener: StationAdapter.StationAdapterListener,
        ): ItemViewHolder<UiStation> {
            return StationViewHolder(
                ViewItemStationBinding.inflate(
                    parent.inflater(),
                    parent,
                    false
                ), listener
            )
        }
    }

    init {
        binding.root.setDebounceOnClickListener {
            listener.onClickStation()
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
            UiType.BTS_SKW -> {
                binding.imgLogo.setImageResource(R.drawable.icon_bts)
                binding.layoutStation.setBackgroundResource(R.drawable.ic_bg_bts_sukhumvit)
                binding.tvStation.text = context.getString(R.string.bts_code_label)
                binding.layoutStationCode.setBackgroundResource(R.drawable.ic_bg_bts_sukhumvit_stroke)
                binding.tvStationCode.setTextColor(context.getColor(R.color.green_sukhumvit))
            }

            UiType.BTS_SL -> {
                binding.imgLogo.setImageResource(R.drawable.icon_bts)
                binding.layoutStation.setBackgroundResource(R.drawable.ic_bg_bts_srilom)
                binding.tvStation.text = context.getString(R.string.bts_code_label)
                binding.layoutStationCode.setBackgroundResource(R.drawable.ic_bg_bts_srilom_stroke)
                binding.tvStationCode.setTextColor(context.getColor(R.color.green_silom))
            }

            UiType.MRT_BLUE -> {
                binding.imgLogo.setImageResource(R.drawable.icon_mrt)
                binding.layoutStation.setBackgroundResource(R.drawable.ic_bg_mrt_blue_line)
                binding.tvStation.text = context.getString(R.string.mrt_code_label)
                binding.layoutStationCode.setBackgroundResource(R.drawable.ic_bg_mrt_blue_line_stroke)
                binding.tvStationCode.setTextColor(context.getColor(R.color.blue_mrt))
            }

            UiType.MRT_PURPLE -> {
                binding.imgLogo.setImageResource(R.drawable.icon_mrt)
                binding.layoutStation.setBackgroundResource(R.drawable.ic_bg_mrt_purple_line)
                binding.tvStation.text = context.getString(R.string.mrt_code_label)
                binding.layoutStationCode.setBackgroundResource(R.drawable.ic_bg_mrt_purple_line_stroke)
                binding.tvStationCode.setTextColor(context.getColor(R.color.purple_mrt))
            }

            UiType.APL -> {
                binding.imgLogo.setImageResource(R.drawable.icon_apl)
                binding.layoutStation.setBackgroundResource(R.drawable.ic_bg_apl)
                binding.tvStation.text = context.getString(R.string.apl_code_label)
                binding.layoutStationCode.setBackgroundResource(R.drawable.ic_bg_apl_stroke)
                binding.tvStationCode.setTextColor(context.getColor(R.color.pink_apl))
            }

            UiType.BTS_G -> {
                binding.imgLogo.setImageResource(R.drawable.icon_bts)
                binding.layoutStation.setBackgroundResource(R.drawable.ic_bg_bts_gold)
                binding.tvStation.text = context.getString(R.string.bts_g_code_label)
                binding.layoutStationCode.setBackgroundResource(R.drawable.ic_bg_bts_gold_stroke)
                binding.tvStationCode.setTextColor(context.getColor(R.color.gold_bts))
            }

            UiType.RED_NORMAL -> {
                binding.imgLogo.setImageResource(R.drawable.icon_srt)
                binding.layoutStation.setBackgroundResource(R.drawable.ic_bg_srtet_red)
                binding.tvStation.text = context.getString(R.string.srtet_code_label)
                binding.layoutStationCode.setBackgroundResource(R.drawable.ic_bg_srtet_red_stroke)
                binding.tvStationCode.setTextColor(context.getColor(R.color.red_srt))
            }

            UiType.RED_WEAK -> {
                binding.imgLogo.setImageResource(R.drawable.icon_srt)
                binding.layoutStation.setBackgroundResource(R.drawable.ic_bg_srtet_red_ligth)
                binding.tvStation.text = context.getString(R.string.srtet_code_label)
                binding.layoutStationCode.setBackgroundResource(R.drawable.ic_bg_srtet_red_ligth_stroke)
                binding.tvStationCode.setTextColor(context.getColor(R.color.red_light_srt))
            }

            UiType.MRT_YELLOW -> {
                binding.imgLogo.setImageResource(R.drawable.icon_mrt_yellow)
                binding.layoutStation.setBackgroundResource(R.drawable.ic_bg_mrt_yellow)
                binding.tvStation.text = context.getString(R.string.mrt_code_label)
                binding.layoutStationCode.setBackgroundResource(R.drawable.ic_bg_mrt_yellow_stroke)
                binding.tvStationCode.setTextColor(context.getColor(R.color.yellow_mrt))
            }

            else -> {
                binding.imgLogo.setImageResource(R.drawable.icon_srt)
                binding.layoutStation.setBackgroundResource(R.drawable.ic_bg_srtet_red_ligth)
                binding.tvStation.text = context.getString(R.string.srtet_code_label)
                binding.layoutStationCode.setBackgroundResource(R.drawable.ic_bg_srtet_red_ligth_stroke)
                binding.tvStationCode.setTextColor(context.getColor(R.color.red_light_srt))
            }
        }
    }
}