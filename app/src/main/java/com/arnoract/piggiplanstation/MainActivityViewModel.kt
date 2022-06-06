package com.arnoract.piggiplanstation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnoract.piggiplanstation.core.CoroutinesDispatcherProvider
import com.arnoract.piggiplanstation.domain.main.GetStationNearByUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(
    private val getStationNearByUseCase: GetStationNearByUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    init {

    }

    fun getStationNearByLatLong() {
        viewModelScope.launch {
            val stations = withContext(coroutinesDispatcherProvider.io) {
                getStationNearByUseCase.invoke(
                    GetStationNearByUseCase.Params(
                        lat = 0.00,
                        long = 0.00
                    )
                )
            }
        }
    }
}