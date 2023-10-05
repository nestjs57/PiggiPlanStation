package com.arnoract.piggiplanstation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnoract.piggiplanstation.core.CoroutinesDispatcherProvider
import com.arnoract.piggiplanstation.core.successOr
import com.arnoract.piggiplanstation.domain.main.FindShortestPathUseCase
import com.arnoract.piggiplanstation.domain.main.GetStationsUseCase
import com.arnoract.piggiplanstation.domain.model.main.Station
import com.arnoract.piggiplanstation.ui.main.dialog.FilterBottomSheetDialog.TypeSelected
import com.arnoract.piggiplanstation.ui.main.dialog.model.UiOverview
import com.arnoract.piggiplanstation.ui.main.mapper.StationToUiStationMapper
import com.arnoract.piggiplanstation.ui.main.model.UiStation
import com.arnoract.piggiplanstation.ui.main.model.UiType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(
    private val getStationsUseCase: GetStationsUseCase,
    private val findShortestPathUseCase: FindShortestPathUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _defaultMaxSizeData = 5

    private val _currentTypeSelected = MutableLiveData(TypeSelected.NONE)
    private val _uiStationsData = MutableLiveData<List<UiStation>>()
    private val _originalUiStations = MutableLiveData<List<UiStation>>()
    private val _maxSizeData = MutableLiveData(_defaultMaxSizeData)
    private val _stations = MutableLiveData<List<Station>>()

    private val _uiStations = MutableLiveData<List<UiStation>>()
    val uiStations: LiveData<List<UiStation>>
        get() = _uiStations

    private val _openDialogFilterType = MutableLiveData<TypeSelected>()
    val openDialogFilterType: LiveData<TypeSelected>
        get() = _openDialogFilterType

    private val _isEnableFilter = MutableLiveData<Boolean>()
    val isEnableFilter: LiveData<Boolean>
        get() = _isEnableFilter

    private val _isShowSeeMore = MutableLiveData<Boolean>()
    val isShowSeeMore: LiveData<Boolean>
        get() = _isShowSeeMore

    private val _scrollToPosition = MutableLiveData<Int>()
    val scrollToPosition: LiveData<Int>
        get() = _scrollToPosition

    private val _locationName = MutableLiveData<String>()
    val locationName: LiveData<String>
        get() = _locationName

    private val _stationNearMe = MutableLiveData<UiStation>()
    val stationNearMe: LiveData<UiStation>
        get() = _stationNearMe

    private val _checkPermissionEvent = MutableLiveData<Unit>()
    val checkPermissionEvent: LiveData<Unit>
        get() = _checkPermissionEvent

    private val _isLoadingLocationNearMe = MutableLiveData<Boolean>()
    val isLoadingLocationNearMe: LiveData<Boolean>
        get() = _isLoadingLocationNearMe

    private val _onClickStationEvent = MutableLiveData<UiOverview>()
    val onClickStationEvent: LiveData<UiOverview>
        get() = _onClickStationEvent

    init {
        viewModelScope.launch {
            _isEnableFilter.value = false
            val stations = withContext(coroutinesDispatcherProvider.io) {
                getStationsUseCase.invoke(Unit).successOr(listOf())
            }
            _stations.value = stations
            _checkPermissionEvent.value = Unit
        }
    }

    fun getStationNearByLatLong(lat: Double, long: Double) {
        viewModelScope.launch {
            _isEnableFilter.value = true
            _currentTypeSelected.value = TypeSelected.NONE
            setDefaultMaxDataSize()

            delay(300)
            _originalUiStations.value = _stations.value?.map {
                StationToUiStationMapper(
                    lat = lat, long = long
                ).map(it)
            }?.sortedBy { it.distance }
            _uiStations.value =
                _originalUiStations.value?.take(_maxSizeData.value ?: _defaultMaxSizeData)
            _uiStationsData.value = _uiStations.value
            setFilterType(_currentTypeSelected.value ?: TypeSelected.NONE)
            setIsShowSeeMore()
            _scrollToPosition.value = 0
        }
    }

    private fun setIsShowSeeMore() {
        val maxSizeOfFilterValue = when (_currentTypeSelected.value) {
            TypeSelected.BTS -> _originalUiStations.value?.count {
                it.type in setOf(UiType.BTS_SKW, UiType.BTS_SL, UiType.BTS_G)
            }

            TypeSelected.MRT -> _originalUiStations.value?.count {
                it.type in setOf(UiType.MRT_BLUE, UiType.MRT_PURPLE, UiType.MRT_YELLOW)
            }

            TypeSelected.APL -> _originalUiStations.value?.count { it.type == UiType.APL }

            TypeSelected.SRT -> _originalUiStations.value?.count {
                it.type in setOf(UiType.RED_NORMAL, UiType.RED_WEAK)
            }

            else -> _originalUiStations.value?.size
        }
        _isShowSeeMore.value =
            (_maxSizeData.value ?: _defaultMaxSizeData) < (maxSizeOfFilterValue ?: 0)
    }

    fun onOpenFilterType() {
        _openDialogFilterType.value = _currentTypeSelected.value
    }

    fun onRefreshLocationNearMe() {
        viewModelScope.launch {
            _isLoadingLocationNearMe.value = true
            delay(1000)
            _isLoadingLocationNearMe.value = false
        }
    }

    fun setFilterType(typeSelected: TypeSelected) {
        _currentTypeSelected.value = typeSelected

        val filteredStations = when (typeSelected) {
            TypeSelected.NONE -> _originalUiStations.value
            TypeSelected.BTS -> _originalUiStations.value?.filter {
                it.type in setOf(
                    UiType.BTS_SKW, UiType.BTS_SL, UiType.BTS_G
                )
            }

            TypeSelected.MRT -> _originalUiStations.value?.filter {
                it.type in setOf(
                    UiType.MRT_BLUE, UiType.MRT_PURPLE, UiType.MRT_YELLOW
                )
            }

            TypeSelected.APL -> _originalUiStations.value?.filter { it.type == UiType.APL }
            TypeSelected.SRT -> _originalUiStations.value?.filter {
                it.type in setOf(
                    UiType.RED_NORMAL, UiType.RED_WEAK
                )
            }
        }
        _uiStations.value = filteredStations?.take(_maxSizeData.value ?: _defaultMaxSizeData)
        setIsShowSeeMore()
    }

    fun setDefaultMaxDataSize() {
        _maxSizeData.value = _defaultMaxSizeData
    }

    fun onClickSeeMore() {
        _maxSizeData.value = _maxSizeData.value?.plus(5)
        setFilterType(_currentTypeSelected.value ?: TypeSelected.NONE)
    }

    fun onClickStation(data: UiStation) {
        viewModelScope.launch {
            val start = _stationNearMe.value?.id
            val end = data.id
            val result = withContext(coroutinesDispatcherProvider.io) {
                findShortestPathUseCase.invoke(
                    FindShortestPathUseCase.Params(
                        startStationId = start ?: "", endStationId = end
                    )
                ).successOr(listOf())
            }
            _onClickStationEvent.value = UiOverview(
                locationToGo = _locationName.value ?: "",
                startStation = _stationNearMe.value,
                distanceBetweenCurrentAndStartStation = _stationNearMe.value?.distanceStr ?: "",
                endStation = data,
                distanceBetweenLastStationAndDestinationLocation = data.distanceStr,
                routes = result,
            )
        }
    }

    fun setLocationNameSelected(value: String) {
        _locationName.value = value
    }

    fun findStationNearMe(lat: Double, long: Double) {
        val uiStation = _stations.value?.map {
            StationToUiStationMapper(
                lat = lat, long = long
            ).map(it)
        }?.minByOrNull { it.distance }
        _stationNearMe.value = uiStation
    }
}