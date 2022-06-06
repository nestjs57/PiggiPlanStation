package com.arnoract.piggiplanstation.common.listener

import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.View
import android.widget.TextView

abstract class DrawableStartTouchListener(textView: TextView) : View.OnTouchListener {

    companion object {
        private const val DRAWABLE_START_INDEX = 0
        private const val EXPAND = 10
    }

    private var drawableStart: Drawable? = textView.compoundDrawablesRelative[DRAWABLE_START_INDEX]

    override fun onTouch(
        v: View,
        event: MotionEvent
    ): Boolean {
        drawableStart?.let {
            if (event.action == MotionEvent.ACTION_DOWN) {
                val x = event.x.toInt()
                val bounds = it.bounds
                if (x >= v.paddingLeft - EXPAND && x <= bounds.width() + v.paddingLeft + EXPAND) {
                    return onDrawableTouch(event)
                }
            }
        }
        return false
    }

    abstract fun onDrawableTouch(event: MotionEvent): Boolean
}
