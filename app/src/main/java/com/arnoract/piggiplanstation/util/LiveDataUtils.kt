package com.arnoract.piggiplanstation.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <T> MutableLiveData<T>.setValueIfNew(newValue: T) {
    if (this.value != newValue) value = newValue
}

fun <T> MutableLiveData<T>.postValueIfNew(newValue: T) {
    if (this.value != newValue) postValue(newValue)
}

fun <T> LiveData<T>.debounce(
    duration: Long = 1000L,
    coroutineScope: CoroutineScope
) = MediatorLiveData<T>().also { mld ->
    val source = this
    var job: Job? = null

    mld.addSource(source) {
        job?.cancel()
        job = coroutineScope.launch {
            delay(duration)
            mld.value = source.value
        }
    }
}

/**
 * An extension to `com.snakydesign.livedataextensions` library for combining four LiveData objects.
 * This function helps combines the latest values from multiple LiveData objects.
 */
fun <W, X, Y, Z, R> combineLatest(
    first: LiveData<W>,
    second: LiveData<X>,
    third: LiveData<Y>,
    fourth: LiveData<Z>,
    combineFunction: (W?, X?, Y?, Z?) -> R
): LiveData<R> {
    val finalLiveData: MediatorLiveData<R> = MediatorLiveData()

    val firstEmit: Emit<W?> = Emit()
    val secondEmit: Emit<X?> = Emit()
    val thirdEmit: Emit<Y?> = Emit()
    val forthEmit: Emit<Z?> = Emit()

    val combine: () -> Unit = {
        if (firstEmit.emitted && secondEmit.emitted && thirdEmit.emitted && forthEmit.emitted) {
            val combined =
                combineFunction(firstEmit.value, secondEmit.value, thirdEmit.value, forthEmit.value)
            finalLiveData.value = combined
        }
    }

    finalLiveData.addSource(first) { value ->
        firstEmit.value = value
        combine()
    }
    finalLiveData.addSource(second) { value ->
        secondEmit.value = value
        combine()
    }
    finalLiveData.addSource(third) { value ->
        thirdEmit.value = value
        combine()
    }
    finalLiveData.addSource(fourth) { value ->
        forthEmit.value = value
        combine()
    }

    return finalLiveData
}

/**
 * Wrapper that wraps an emitted value.
 */
private class Emit<T> {

    internal var emitted: Boolean = false

    internal var value: T? = null
        set(value) {
            field = value
            emitted = true
        }

    fun reset() {
        value = null
        emitted = false
    }
}
