package com.arnoract.piggiplanstation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnoract.piggiplanstation.core.CoroutinesDispatcherProvider
import com.arnoract.piggiplanstation.core.successOr
import com.arnoract.piggiplanstation.domain.main.GetStationsUseCase
import com.arnoract.piggiplanstation.ui.main.mapper.StationToUiStationMapper
import com.arnoract.piggiplanstation.ui.main.model.UiStation
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(
    private val getStationsUseCase: GetStationsUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _uiStations = MutableLiveData<List<UiStation>>()
    val uiStations: LiveData<List<UiStation>>
        get() = _uiStations

    init {
        initDefaultStations()
    }

    private fun initDefaultStations() {
        viewModelScope.launch {
            val stations = withContext(coroutinesDispatcherProvider.io) {
                getStationsUseCase.invoke(Unit).successOr(listOf())
            }
            _uiStations.value = stations.map {
                StationToUiStationMapper(
                    lat = 0.0,
                    long = 0.0
                ).map(it).copy(
                    isShowDistance = false
                )
            }
        }
    }

    fun getStationNearByLatLong(lat: Double, long: Double) {
        viewModelScope.launch {
            val stations = withContext(coroutinesDispatcherProvider.io) {
                getStationsUseCase.invoke(Unit).successOr(listOf())
            }
            _uiStations.value = stations.map {
                StationToUiStationMapper(
                    lat = lat,
                    long = long
                ).map(it)
            }.sortedBy { it.distance }

        }
    }
}