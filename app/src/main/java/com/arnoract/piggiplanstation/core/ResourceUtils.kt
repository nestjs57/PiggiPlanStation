package com.arnoract.piggiplanstation.core

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.DimenRes

fun Context.dimenToPx(
    @DimenRes
    dimenRes: Int
) = try {
    this.resources.getDimensionPixelOffset(dimenRes)
} catch (ignore: Resources.NotFoundException) {
    0
}

fun Context.dpToPx(sizeInDp: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        sizeInDp,
        this.resources.displayMetrics
    ).toInt()
}

fun Context.resolveAttribute(@AttrRes attrRes: Int): Int {
    return with(TypedValue()) {
        this@resolveAttribute.theme.resolveAttribute(attrRes, this, true)
        resourceId
    }
}
