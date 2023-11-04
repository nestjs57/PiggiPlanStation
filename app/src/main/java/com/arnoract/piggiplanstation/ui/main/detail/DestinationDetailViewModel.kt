package com.arnoract.piggiplanstation.ui.main.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnoract.piggiplanstation.core.CoroutinesDispatcherProvider
import com.arnoract.piggiplanstation.core.successOr
import com.arnoract.piggiplanstation.domain.main.FindShortestPathUseCase
import com.arnoract.piggiplanstation.domain.main.GetStationsUseCase
import com.arnoract.piggiplanstation.domain.model.main.RouteStation
import com.arnoract.piggiplanstation.domain.model.main.Station
import com.arnoract.piggiplanstation.ui.main.mapper.TypeToUiTypeMapper
import com.arnoract.piggiplanstation.ui.main.model.UiType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DestinationDetailViewModel(
    private val stationEndId: String,
    private val getStationsUseCase: GetStationsUseCase,
    private val findShortestPathUseCase: FindShortestPathUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _stationStartId = MutableLiveData<String>()
    private val _stations = MutableLiveData<List<Station>>()

    private val _startLocationCode = MutableLiveData<UiType>()
    val startLocationCode: LiveData<UiType>
        get() = _startLocationCode

    private val _startLocationName = MutableLiveData<String>()
    val startLocationName: LiveData<String>
        get() = _startLocationName

    private val _endLocationCode = MutableLiveData<UiType>()
    val endLocationCode: LiveData<UiType>
        get() = _endLocationCode

    private val _endLocationName = MutableLiveData<String>()
    val endLocationName: LiveData<String>
        get() = _endLocationName

    private val _isLoadingState = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoadingState

    private val _routes = MutableStateFlow<List<RouteStation>>(listOf())
    val routes: StateFlow<List<RouteStation>> get() = _routes.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val stations = withContext(coroutinesDispatcherProvider.io) {
                    getStationsUseCase.invoke(Unit).successOr(listOf())
                }
                _stations.value = stations

                val endLocation = _stations.value?.firstOrNull { it.id == stationEndId }
                _endLocationCode.value = TypeToUiTypeMapper.map(endLocation?.type ?: 0)
                _endLocationName.value = endLocation?.name_th
            } catch (_: Exception) {

            } finally {
                _isLoadingState.value = false
            }
        }
    }

    fun setStationSelected(id: String?) {
        id ?: return
        _stationStartId.value = id
        val startLocation = _stations.value?.firstOrNull { it.id == id }
        _startLocationCode.value = TypeToUiTypeMapper.map(startLocation?.type ?: 0)
        _startLocationName.value = startLocation?.name_th
        onFindShortestPath()
    }

    private fun onFindShortestPath() {
        viewModelScope.launch {
            val result = withContext(coroutinesDispatcherProvider.io) {
                findShortestPathUseCase.invoke(
                    FindShortestPathUseCase.Params(
                        startStationId = _stationStartId.value ?: "", endStationId = stationEndId
                    )
                ).successOr(listOf())
            }
            _routes.value = result
        }
    }
}