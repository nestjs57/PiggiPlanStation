package com.arnoract.piggiplanstation.core

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(
	@LayoutRes
	layout: Int,
	attachToRoot: Boolean = false
): View {
	return LayoutInflater.from(this.context).inflate(layout, this, attachToRoot)
}

inline fun <T : View> T.visibleIf(
	isVisible: Boolean,
	notVisibleState: Int = View.GONE,
	crossinline whenVisible: (T.() -> Unit)
) {
	if (isVisible) {
		whenVisible.invoke(this)
		this.visibility = View.VISIBLE
	} else {
		this.visibility = notVisibleState
	}
}

inline fun <T : View, U : CharSequence> T.visibleIfNotBlank(
	text: U?,
	notVisibleState: Int = View.GONE,
	crossinline whenVisible: (T.(U) -> Unit)
) {
	if (text?.isNotBlank() == true) {
		whenVisible.invoke(this, text)
		this.visibility = View.VISIBLE
	} else {
		this.visibility = notVisibleState
	}
}

inline fun <T : View, U> T.visibleIfNotNull(
	data: U?,
	notVisibleState: Int = View.GONE,
	crossinline whenVisible: (T.(U) -> Unit)
) {
	if (data != null) {
		whenVisible.invoke(this, data)
		this.visibility = View.VISIBLE
	} else {
		this.visibility = notVisibleState
	}
}
fun View.addRipple() = with(TypedValue()) {
	context.theme.resolveAttribute(android.R.attr.selectableItemBackground, this, true)
	setBackgroundResource(resourceId)
}

fun View.setDebounceOnClickListener(
	intervalMillis: Long = 300,
	doClick: (View) -> Unit
) {
	setOnClickListener(DebounceOnClickListener(intervalMillis = intervalMillis, doClick = doClick))
}

private class DebounceOnClickListener(
	private val intervalMillis: Long,
	private val doClick: ((View) -> Unit)
) : View.OnClickListener {

	override fun onClick(v: View) {
		if (enabled) {
			enabled = false
			v.postDelayed(ENABLE_AGAIN, intervalMillis)
			doClick(v)
		}
	}

	companion object {
		@JvmStatic
		var enabled = true
		private val ENABLE_AGAIN = Runnable { enabled = true }
	}
}
