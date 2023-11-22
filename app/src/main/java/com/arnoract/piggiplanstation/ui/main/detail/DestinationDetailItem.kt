package com.arnoract.piggiplanstation.ui.main.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnoract.piggiplanstation.R
import com.arnoract.piggiplanstation.domain.model.main.RouteStation
import com.arnoract.piggiplanstation.ui.main.mapper.TypeToUiTypeMapper
import com.arnoract.piggiplanstation.ui.main.model.UiType

@Composable
fun DestinationDetailItem(data: RouteStation) {

    val customFontFamily = FontFamily(
        Font(R.font.mn_paethai_bystorylog_regular, FontWeight.Normal),
    )

    val backgroundColor = when (TypeToUiTypeMapper.map(data.type)) {
        UiType.BTS_SKW -> R.color.green_sukhumvit
        UiType.BTS_SL -> R.color.green_silom
        UiType.MRT_BLUE -> R.color.blue_mrt
        UiType.MRT_PURPLE -> R.color.purple_mrt
        UiType.APL -> R.color.pink_apl
        UiType.BTS_G -> R.color.gold_bts
        UiType.RED_NORMAL -> R.color.red_srt
        UiType.RED_WEAK -> R.color.red_light_srt
        UiType.MRT_YELLOW -> R.color.yellow_mrt
        UiType.MRT_PINK -> R.color.pink_mrt
    }

    Column {
        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(colorResource(id = backgroundColor)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    color = colorResource(id = R.color.white),
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Bold,
                    text = data.id,
                    modifier = Modifier.padding(end = 4.dp),
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    color = colorResource(id = R.color.black),
                    fontFamily = customFontFamily,
                    text = data.nameTh,
                    modifier = Modifier.padding(end = 4.dp),
                    fontSize = 16.sp,
                    maxLines = 1
                )
                Text(
                    color = colorResource(id = R.color.gray500),
                    fontFamily = customFontFamily,
                    text = data.nameEn,
                    modifier = Modifier.padding(end = 4.dp),
                    fontSize = 16.sp,
                    maxLines = 1,
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(start = 22.dp)
                .height(30.dp)
                .width(4.dp)
                .border(
                    width = 2.dp,
                    color = colorResource(id = R.color.gray300),
                    shape = DottedVerticalShape(step = 8.dp) // Adjust the step as needed
                )
        )
    }
}