package com.arnoract.piggiplanstation.ui.main.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnoract.piggiplanstation.core.CoroutinesDispatcherProvider
import com.arnoract.piggiplanstation.core.successOr
import com.arnoract.piggiplanstation.domain.main.GetStationsUseCase
import com.arnoract.piggiplanstation.domain.model.main.Station
import com.arnoract.piggiplanstation.ui.main.mapper.StationToUiStationMapper
import com.arnoract.piggiplanstation.ui.main.model.UiStation
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SelectStationViewModel(
    private val getStationsUseCase: GetStationsUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _stations = MutableLiveData<List<Station>>()

    private val _originalUiStations = MutableLiveData<List<UiStation>>()
    private val _uiStations = MutableLiveData<List<UiStation>>()
    val uiStations: LiveData<List<UiStation>>
        get() = _uiStations

    init {
        viewModelScope.launch {
            val stations = withContext(coroutinesDispatcherProvider.io) {
                getStationsUseCase.invoke(Unit).successOr(listOf())
            }
            _stations.value = stations

            _uiStations.value = _stations.value?.map {
                StationToUiStationMapper(
                    lat = 0.0, long = 0.0, ignoreDistance = true
                ).map(it).copy(isShowOnlyItem = true, isShowDistance = false)
            }
            _originalUiStations.value = _uiStations.value
        }
    }

    fun onSearchChanged(text: String) {
        if (_stations.value?.none { it.name_th.contains(text) } == true) {
            _uiStations.value = listOf()
        } else {
            _uiStations.value = _originalUiStations.value?.filter { it.name_th.contains(text) }
        }
    }
}