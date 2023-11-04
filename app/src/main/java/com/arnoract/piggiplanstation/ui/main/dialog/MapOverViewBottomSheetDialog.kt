package com.arnoract.piggiplanstation.ui.main.dialog

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnoract.piggiplanstation.R
import com.arnoract.piggiplanstation.core.setDebounceOnClickListener
import com.arnoract.piggiplanstation.databinding.BottomDialogMapOverviewBinding
import com.arnoract.piggiplanstation.domain.model.main.RouteStation
import com.arnoract.piggiplanstation.ui.main.dialog.model.UiOverview
import com.arnoract.piggiplanstation.ui.main.mapper.TypeToUiTypeMapper
import com.arnoract.piggiplanstation.ui.main.model.UiType
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.math.roundToInt

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
        binding.tvTitle.text = "รายละเอียดเส้นทาง"
        binding.imvClose.setDebounceOnClickListener {
            dismiss()
        }

        binding.composeView.setContent {
            Content()
        }
    }

    @Composable
    fun Content() {
        val newData = uiOverview?.routes?.groupBy { it.type }
        val customFontFamily = FontFamily(
            Font(R.font.db_heavent_now, FontWeight.Normal),
        )
        LazyColumn {
            item { Spacer(modifier = Modifier.padding(top = 8.dp)) }
            item { HeaderInfo(uiOverview) }
            item {
                val changeStationCount =
                    if (newData?.isEmpty() == true) 0 else newData?.size?.minus(1)
                val totalStation = uiOverview?.routes?.size
                Text(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(top = 16.dp),
                    fontFamily = customFontFamily,
                    text = "เปลี่ยนสถานี $changeStationCount สถานี | ทั้งหมด $totalStation สถานี"
                )
            }
            item {
                Divider(
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = colorResource(id = R.color.gray200)
                )
            }
            item {
                Column {
                    Text(
                        fontFamily = customFontFamily,
                        text = "ข้อมูลเส้นทาง",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
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
                                .height(56.dp)
                                .width(4.dp)
                                .border(
                                    width = 2.dp,
                                    color = Color.Black,
                                    shape = DottedVerticalShape(step = 8.dp) // Adjust the step as needed
                                )
                        )
                        Spacer(modifier = Modifier.padding(end = 8.dp))
                        Text(
                            fontFamily = customFontFamily,
                            color = colorResource(id = R.color.gray700),
                            text = uiOverview?.distanceBetweenCurrentAndStartStation ?: ""
                        )
                    }
                }

            }

            val resultPairs: List<Pair<RouteStation, List<RouteStation>>> =
                newData?.values?.map { stationsOfType ->
                    val firstStation = stationsOfType.firstOrNull()
                    if (firstStation != null) {
                        Pair(firstStation, stationsOfType.drop(1))
                    } else {
                        Pair(RouteStation("", "", "", 0, ""), emptyList())
                    }
                } ?: listOf()

            items(resultPairs.size) {
                ItemRoute(
                    data = resultPairs[it], it, it == resultPairs.size.minus(1)
                )
            }

            item {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Row(
                        modifier = Modifier.padding(start = 7.dp, top = 0.dp, bottom = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .height(56.dp)
                                .width(4.dp)
                                .border(
                                    width = 2.dp,
                                    color = Color.Black,
                                    shape = DottedVerticalShape(step = 8.dp) // Adjust the step as needed
                                )
                        )
                        Spacer(modifier = Modifier.padding(end = 8.dp))
                        Text(
                            fontFamily = customFontFamily,
                            color = colorResource(id = R.color.gray700),
                            text = uiOverview?.distanceBetweenLastStationAndDestinationLocation
                                ?: ""
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
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
fun HeaderInfo(uiOverview: UiOverview?) {
    val bgStartStation = when (uiOverview?.startStation?.type) {
        UiType.BTS_SKW -> R.color.green_sukhumvit
        UiType.BTS_SL -> R.color.green_silom
        UiType.MRT_BLUE -> R.color.blue_mrt
        UiType.MRT_PURPLE -> R.color.purple_mrt
        UiType.APL -> R.color.pink_apl
        UiType.BTS_G -> R.color.gold_bts
        UiType.RED_NORMAL -> R.color.red_srt
        UiType.RED_WEAK -> R.color.red_light_srt
        UiType.MRT_YELLOW -> R.color.yellow_mrt
        else -> {
            R.color.green_sukhumvit
        }
    }
    val bgEndStation = when (uiOverview?.endStation?.type) {
        UiType.BTS_SKW -> R.color.green_sukhumvit
        UiType.BTS_SL -> R.color.green_silom
        UiType.MRT_BLUE -> R.color.blue_mrt
        UiType.MRT_PURPLE -> R.color.purple_mrt
        UiType.APL -> R.color.pink_apl
        UiType.BTS_G -> R.color.gold_bts
        UiType.RED_NORMAL -> R.color.red_srt
        UiType.RED_WEAK -> R.color.red_light_srt
        UiType.MRT_YELLOW -> R.color.yellow_mrt
        else -> {
            R.color.green_sukhumvit
        }
    }
    Row {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircleExample()
            Box(
                modifier = Modifier
                    .height(56.dp)
                    .width(4.dp)
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = DottedVerticalShape(step = 8.dp) // Adjust the step as needed
                    )
            )
            CircleExample()
        }
        Column(
            modifier = Modifier
                .height(96.dp)
                .padding(start = 8.dp)
        ) {
            val customFontFamily = FontFamily(
                Font(R.font.db_heavent_now, FontWeight.Normal),
            )
            Text(
                color = colorResource(id = R.color.black),
                fontFamily = customFontFamily,
                text = "${uiOverview?.startStation?.name_th} | ${uiOverview?.startStation?.name_en}"
            )
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .background(
                        colorResource(id = bgStartStation), shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    color = colorResource(id = R.color.white),
                    fontFamily = customFontFamily,
                    fontSize = 12.sp,
                    text = uiOverview?.startStation?.type_name ?: ""
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                color = colorResource(id = R.color.black),
                fontFamily = customFontFamily,
                text = "${uiOverview?.endStation?.name_th} | ${uiOverview?.endStation?.name_en}"
            )
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .background(
                        colorResource(id = bgEndStation), shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    color = colorResource(id = R.color.white),
                    fontFamily = customFontFamily,
                    fontSize = 12.sp,
                    text = uiOverview?.endStation?.type_name ?: ""
                )
            }
        }
    }
}

@Composable
fun ItemRoute(
    data: Pair<RouteStation, List<RouteStation>>, position: Int, isLastPosition: Boolean
) {

    val customFontFamily = FontFamily(
        Font(R.font.db_heavent_now, FontWeight.Normal),
    )

    val icon = when (TypeToUiTypeMapper.map(data.first.type)) {
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

    val prefix = when {
        position == 0 -> "เริ่มที่"
        else -> "เปลี่ยนที่"
    }

    val prefixColor = when (TypeToUiTypeMapper.map(data.first.type)) {
        UiType.BTS_SKW -> R.color.green_sukhumvit
        UiType.BTS_SL -> R.color.green_silom
        UiType.MRT_BLUE -> R.color.blue_mrt
        UiType.MRT_PURPLE -> R.color.purple_mrt
        UiType.APL -> R.color.pink_apl
        UiType.BTS_G -> R.color.gold_bts
        UiType.RED_NORMAL -> R.color.red_srt
        UiType.RED_WEAK -> R.color.red_light_srt
        UiType.MRT_YELLOW -> R.color.gold_bts
    }

    Column {
        Row(modifier = Modifier.padding(vertical = if (isLastPosition && data.second.isEmpty()) 0.dp else 4.dp)) {
//            CircleExample()
            Image(
                painter = painterResource(id = icon),
                modifier = Modifier.size(20.dp),
                contentDescription = "View more"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Row {
                Text(
                    color = colorResource(id = prefixColor),
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Bold,
                    text = prefix,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    color = colorResource(id = R.color.black),
                    fontFamily = customFontFamily,
                    text = data.first.nameTh
                )
            }
        }
    }

    val lineColor = when (TypeToUiTypeMapper.map(data.first.type)) {
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

    if (isLastPosition && data.second.isEmpty()) {

    } else {
        Column(Modifier.wrapContentHeight()) {
            Row(
                modifier = Modifier.padding(start = 7.dp, top = 2.dp, bottom = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentWidth()
                        .fillMaxHeight()
                        .padding(end = 8.dp)
                        .background(
                            colorResource(id = lineColor), shape = RoundedCornerShape(16.dp)
                        )
                        .padding(bottom = 8.dp, top = 8.dp)

                ) {
                    val formattedString = if (data.second.isEmpty()) {
                        "a"
                    } else {
                        data.second.map {
                            "a"
                        }.joinToString("\n") { it }
                    }
                    Text(
                        modifier = Modifier.fillMaxHeight(),
                        color = colorResource(lineColor),
                        fontFamily = customFontFamily,
                        text = formattedString
                    )
                }
                if (data.second.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .background(
                                colorResource(id = R.color.gray100),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                    ) {
                        val formattedString = data.second.joinToString("\n") { it.nameTh.trim() }
                        Text(
                            modifier = Modifier.fillMaxHeight(), // Set the Text to fill the available height
                            color = colorResource(id = R.color.black),
                            fontFamily = customFontFamily,
                            text = formattedString
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CircleExample(color: Int = R.color.black) {

    val colorFromResource = colorResource(id = color)

    Canvas(
        modifier = Modifier.size(20.dp)
    ) {
        // Define the circle's center and radius
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val radius = size.minDimension / 2f

        // Draw the circle
        drawCircle(
            color = colorFromResource, // Circle color
            center = Offset(centerX, centerY), // Center of the circle
            radius = radius - 4.dp.toPx(), // Subtract a small value for stroke width
            style = Stroke(2.dp.toPx()) // Stroke width
        )
    }
}

private data class DottedVerticalShape(
    val step: Dp,
) : Shape {
    override fun createOutline(
        size: Size, layoutDirection: LayoutDirection, density: Density
    ) = Outline.Generic(Path().apply {
        val stepPx = with(density) { step.toPx() }
        val stepsCount = (size.height / stepPx).roundToInt()
        val actualStep = size.height / stepsCount
        val dotSize = Size(width = size.width, height = actualStep / 2)
        for (i in 0 until stepsCount) {
            addRect(
                Rect(
                    offset = Offset(x = 0f, y = i * actualStep), size = dotSize
                )
            )
        }
        close()
    })
}