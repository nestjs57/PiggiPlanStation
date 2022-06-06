package com.arnoract.piggiplanstation.base.recyclerview

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class ItemViewHolder<in T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
	protected val context: Context
		get() = itemView.context

	abstract fun fillData(
		data: T?,
		position: Int
	)

	open fun fillData(
		data: T?,
		position: Int,
		payloads: List<Any?>
	) {
		//Optional
	}
}
