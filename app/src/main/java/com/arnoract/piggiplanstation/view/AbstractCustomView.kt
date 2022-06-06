package com.arnoract.piggiplanstation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout

abstract class AbstractCustomView<T> : FrameLayout {

    protected var data: T? = null
    protected open var isHideOnNull = true
    private var listener: OnTypeClickListener<T>? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        this.extractAttributes(attrs, 0, 0)
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        this.extractAttributes(attrs, defStyleAttr, 0)
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        this.extractAttributes(attrs, defStyleAttr, defStyleRes)
        init()
    }

    protected open fun extractAttributes(
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        //Optionally override.
    }

    private fun init() {
        inflateLayout(LayoutInflater.from(context))

        setUpView()

        if (isInEditMode) {
            fillDataInEditMode()
            return
        }
        setOnClickListener {
            listener?.onClick(it, data)
        }
    }

    abstract fun inflateLayout(inflater: LayoutInflater)

    protected open fun setUpView() {
        //Optionally override.
    }

    protected open fun fillDataInEditMode() {
        //Optionally override.
    }

    protected open fun fillDataNullable(d: T?) {
        //Optionally override.
    }

    protected open fun fillDataNonNull(d: T) {
        //Optionally override.
    }

    fun fillData(d: T?) {
        try {
            this.data = d
            fillDataNullable(d)
            if (d != null) {
                visibility = View.VISIBLE
                fillDataNonNull(d)
            } else {
                if (isHideOnNull) {
                    visibility = View.GONE
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            visibility = View.GONE
        }
    }

    fun setOnClickListener(listener: OnTypeClickListener<T>) {
        this.listener = listener
    }

    public interface OnTypeClickListener<in T> {
        fun onClick(
            view: View,
            data: T?
        )
    }
}
