package com.arnoract.piggiplanstation.common.listener

import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.View
import android.widget.TextView

abstract class DrawableEndTouchListener(textView: TextView) : View.OnTouchListener {

    companion object {
        private const val DRAWABLE_END_INDEX = 2
        private const val EXPAND = 10
    }

    private var drawableEnd: Drawable? = textView.compoundDrawablesRelative[DRAWABLE_END_INDEX]

    override fun onTouch(
        v: View,
        event: MotionEvent
    ): Boolean {
        drawableEnd?.let {
            if (event.action == MotionEvent.ACTION_DOWN) {
                val x = event.x.toInt()
                val y = event.y.toInt()
                val bounds = it.bounds
                if (x >= v.right - bounds.width() - v.paddingRight - EXPAND && x <= v.right - v.paddingRight + EXPAND && y >= v.paddingTop - EXPAND && y <= v.height - v.paddingBottom + EXPAND) {
                    return onDrawableTouch(event)
                }
            }
        }
        return false
    }

    abstract fun onDrawableTouch(event: MotionEvent): Boolean
}