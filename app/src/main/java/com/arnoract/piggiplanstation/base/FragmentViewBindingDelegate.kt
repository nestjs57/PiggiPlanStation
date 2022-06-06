package com.arnoract.piggiplanstation.base

import android.databinding.tool.writer.ViewBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentViewBindingDelegate<T : ViewBinding>(
	val fragment: Fragment,
	val viewBindingFactory: (View) -> T,
) : ReadOnlyProperty<Fragment, T> {
	private var binding: T? = null

	init {
		fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
			override fun onCreate(owner: LifecycleOwner) {
				fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
					viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
						override fun onDestroy(owner: LifecycleOwner) {
							binding = null
						}
					})
				}
			}
		})
	}

	override fun getValue(
		thisRef: Fragment,
		property: KProperty<*>,
	): T {
		val binding = binding
		if (binding != null) {
			return binding
		}

		val lifecycle = fragment.viewLifecycleOwner.lifecycle
		if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
			throw IllegalStateException("Should not attempt to get bindings when Fragment views are destroyed.")
		}

		return viewBindingFactory(thisRef.requireView()).also {
			this@FragmentViewBindingDelegate.binding = it
		}
	}
}

fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) = FragmentViewBindingDelegate(
	this,
	viewBindingFactory)

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(crossinline bindingInflater: (LayoutInflater) -> T) = lazy(
	LazyThreadSafetyMode.NONE) {
	bindingInflater.invoke(layoutInflater)
}

@Suppress("NOTHING_TO_INLINE")
inline fun ViewGroup.inflater() = LayoutInflater.from(this.context)!!
