package com.arnoract.piggiplanstation.ui.main.dialog

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arnoract.piggiplanstation.R
import com.arnoract.piggiplanstation.core.setDebounceOnClickListener
import com.arnoract.piggiplanstation.databinding.BottomDialogMapOverviewBinding
import com.arnoract.piggiplanstation.domain.model.main.RouteStation
import com.arnoract.piggiplanstation.ui.main.dialog.model.UiOverview
import com.arnoract.piggiplanstation.ui.main.mapper.TypeToUiTypeMapper
import com.arnoract.piggiplanstation.ui.main.model.UiType
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MapOverViewBottomSheetDialog : BottomSheetDialogFragment() {

    private var uiOverview: UiOverview? = null
    private lateinit var binding: BottomDialogMapOverviewBinding

    companion object {
        fun newInstance(uiOverview: UiOverview) = MapOverViewBottomSheetDialog().apply {
            this.uiOverview = uiOverview
        }
    }

    override fun onStart() {
        super.onStart()

        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)

        val screenHeight = displayMetrics.heightPixels
        val targetHeight = (screenHeight * 0.8).toInt()

        dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            ?.let { bottomSheet ->
                val behavior = BottomSheetBehavior.from(bottomSheet)
                behavior.peekHeight = screenHeight
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                bottomSheet.layoutParams.height = targetHeight
                bottomSheet.requestLayout()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = BottomDialogMapOverviewBinding.inflate(inflater, container, false)

        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels
        val layoutParams = view?.layoutParams
        layoutParams?.height = screenHeight
        view?.layoutParams = layoutParams

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.tvTitle.text = "ไปยัง ${uiOverview?.locationToGo}"
        binding.imvClose.setDebounceOnClickListener {
            dismiss()
        }

        binding.composeView.setContent {
            Content()
        }
    }

    @Composable
    fun Content() {
        val customFontFamily = FontFamily(
            Font(R.font.db_heavent_now, FontWeight.Normal),
        )
        LazyColumn {
            item { Spacer(modifier = Modifier.padding(top = 8.dp)) }
            item {
                Column {
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.pin_blue),
                            modifier = Modifier.size(20.dp),
                            tint = colorResource(id = R.color.blue_pin),
                            contentDescription = "View more"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(fontFamily = customFontFamily, text = "ที่อยู่ปัจจุบัน")
                    }
                    Row(
                        modifier = Modifier.padding(start = 7.dp, top = 4.dp, bottom = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .background(
                                    colorResource(id = R.color.blue_pin),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .width(6.dp)
                                .height(56.dp)
                        )
                        Text(
                            fontFamily = customFontFamily,
                            color = colorResource(id = R.color.gray700),
                            text = "ห่างจาก ${uiOverview?.startStation?.name_th} : ${uiOverview?.distanceBetweenCurrentAndStartStation ?: ""}"
                        )
                    }
                }
            }
            items(uiOverview?.routes?.size ?: 0) {
                val data = uiOverview?.routes?.get(it)
                val isLastItem = it == uiOverview?.routes?.size?.minus(1)
                ItemRoute(data, isLastItem)
            }
            item {
                Column {
                    Row(
                        modifier = Modifier.padding(start = 7.dp, top = 0.dp, bottom = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .background(
                                    colorResource(id = R.color.red_pin),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .width(6.dp)
                                .height(56.dp)
                        )
                        Text(
                            fontFamily = customFontFamily,
                            color = colorResource(id = R.color.gray700),
                            text = "ห่างจาก ${uiOverview?.locationToGo} : ${uiOverview?.distanceBetweenLastStationAndDestinationLocation ?: ""}"
                        )
                    }
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.pin),
                            modifier = Modifier.size(20.dp),
                            contentDescription = "View more"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(fontFamily = customFontFamily, text = uiOverview?.locationToGo ?: "")
                    }
                }
            }
            item { Spacer(modifier = Modifier.padding(bottom = 16.dp)) }
        }
    }
}

@Composable
fun ItemRoute(data: RouteStation?, isLastItem: Boolean) {
    val icon = when (TypeToUiTypeMapper.map(data?.type ?: 0)) {
        UiType.BTS_SKW -> R.drawable.icon_bts
        UiType.BTS_SL -> R.drawable.icon_bts
        UiType.MRT_BLUE -> R.drawable.icon_mrt
        UiType.MRT_PURPLE -> R.drawable.icon_mrt
        UiType.APL -> R.drawable.icon_apl
        UiType.BTS_G -> R.drawable.icon_bts
        UiType.RED_NORMAL -> R.drawable.icon_srt
        UiType.RED_WEAK -> R.drawable.icon_srt
        UiType.MRT_YELLOW -> R.drawable.icon_mrt_yellow
    }
    val lineColor = when (TypeToUiTypeMapper.map(data?.type ?: 0)) {
        UiType.BTS_SKW -> R.color.green_sukhumvit
        UiType.BTS_SL -> R.color.green_silom
        UiType.MRT_BLUE -> R.color.blue_mrt
        UiType.MRT_PURPLE -> R.color.purple_mrt
        UiType.APL -> R.color.pink_apl
        UiType.BTS_G -> R.color.gold_bts
        UiType.RED_NORMAL -> R.color.red_srt
        UiType.RED_WEAK -> R.color.red_light_srt
        UiType.MRT_YELLOW -> R.color.yellow_mrt
    }

    val fontColor = when (TypeToUiTypeMapper.map(data?.type ?: 0)) {
        UiType.BTS_SKW -> R.color.green_sukhumvit
        UiType.BTS_SL -> R.color.green_silom
        UiType.MRT_BLUE -> R.color.blue_mrt
        UiType.MRT_PURPLE -> R.color.purple_mrt
        UiType.APL -> R.color.pink_apl
        UiType.BTS_G -> R.color.gold_bts
        UiType.RED_NORMAL -> R.color.red_srt
        UiType.RED_WEAK -> R.color.red_light_srt
        UiType.MRT_YELLOW -> R.color.black
    }

    val customFontFamily = FontFamily(
        Font(R.font.db_heavent_now, FontWeight.Normal),
    )
    Column {
        Row(modifier = Modifier.padding(vertical = 4.dp)) {
            Image(
                painter = painterResource(id = icon),
                modifier = Modifier.size(20.dp),
                contentDescription = "View more"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                color = colorResource(id = fontColor),
                fontFamily = customFontFamily,
                text = "${data?.nameTh}"
            )

        }
        if (!isLastItem) {
            Row(
                modifier = Modifier.padding(start = 7.dp, top = 2.dp, bottom = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .background(
                            colorResource(id = lineColor), shape = RoundedCornerShape(16.dp)
                        )
                        .width(6.dp)
                        .height(28.dp)
                )
            }
        }
    }
}

@Composable
fun CircleExample() {
    Canvas(
        modifier = Modifier.size(20.dp)
    ) {
        // Define the circle's center and radius
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val radius = size.minDimension / 2f

        // Draw the circle
        drawCircle(
            color = Color.Black, // Circle color
            center = Offset(centerX, centerY), // Center of the circle
            radius = radius - 4.dp.toPx(), // Subtract a small value for stroke width
            style = Stroke(2.dp.toPx()) // Stroke width
        )
    }
}