package com.arnoract.piggiplanstation.ui.main.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnoract.piggiplanstation.R

@Composable
fun DestinationDetailFooter(locationEnd: String, distance: String?) {

    val customFontFamily = FontFamily(
        Font(R.font.mn_paethai_bystorylog_regular, FontWeight.Normal),
    )

    Row(
        modifier = Modifier.padding(start = 4.dp, top = 4.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.pin),
            modifier = Modifier
                .size(40.dp),
            contentDescription = "View more"
        )
        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                color = colorResource(id = R.color.black),
                fontFamily = customFontFamily,
                text = locationEnd,
                modifier = Modifier.padding(end = 4.dp),
                fontSize = 16.sp,
                maxLines = 1
            )
            Text(
                color = colorResource(id = R.color.gray500),
                fontFamily = customFontFamily,
                text = distance ?: "-",
                modifier = Modifier.padding(end = 4.dp),
                fontSize = 16.sp,
                maxLines = 1,
            )
        }
    }
}