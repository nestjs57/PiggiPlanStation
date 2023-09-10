package com.arnoract.piggiplanstation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnoract.piggiplanstation.core.CoroutinesDispatcherProvider
import com.arnoract.piggiplanstation.core.successOr
import com.arnoract.piggiplanstation.domain.main.GetStationsUseCase
import com.arnoract.piggiplanstation.ui.main.dialog.FilterBottomSheetDialog.TypeSelected
import com.arnoract.piggiplanstation.ui.main.mapper.StationToUiStationMapper
import com.arnoract.piggiplanstation.ui.main.model.UiStation
import com.arnoract.piggiplanstation.ui.main.model.UiType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(
    private val getStationsUseCase: GetStationsUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _currentTypeSelected = MutableLiveData(TypeSelected.NONE)
    private val _uiStationsData = MutableLiveData<List<UiStation>>()

    private val _uiStations = MutableLiveData<List<UiStation>>()
    val uiStations: LiveData<List<UiStation>>
        get() = _uiStations

    private val _openDialogFilterType = MutableLiveData<TypeSelected>()
    val openDialogFilterType: LiveData<TypeSelected>
        get() = _openDialogFilterType

    private val _isEnableFilter = MutableLiveData<Boolean>()
    val isEnableFilter: LiveData<Boolean>
        get() = _isEnableFilter

    init {
        _isEnableFilter.value = false
    }

    fun getStationNearByLatLong(lat: Double, long: Double) {
        _isEnableFilter.value = true
        viewModelScope.launch {
            val stations = withContext(coroutinesDispatcherProvider.io) {
                getStationsUseCase.invoke(Unit).successOr(listOf())
            }
            delay(300)
            _uiStations.value = stations.map {
                StationToUiStationMapper(
                    lat = lat,
                    long = long
                ).map(it)
            }.sortedBy { it.distance }
            _uiStationsData.value = _uiStations.value
            setFilterType(_currentTypeSelected.value ?: TypeSelected.NONE)
        }
    }

    fun onOpenFilterType() {
        _openDialogFilterType.value = _currentTypeSelected.value
    }

    fun setFilterType(typeSelected: TypeSelected) {
        _currentTypeSelected.value = typeSelected
        when (typeSelected) {
            TypeSelected.NONE -> {
                _uiStations.value = _uiStationsData.value
            }
            TypeSelected.BTS -> {
                _uiStations.value =
                    _uiStationsData.value?.filter {
                        it.type == UiType.BTS_SKW || it.type == UiType.BTS_SL || it.type == UiType.BTS_G
                    }
            }
            TypeSelected.MRT -> {
                _uiStations.value =
                    _uiStationsData.value?.filter {
                        it.type == UiType.MRT_BLUE || it.type == UiType.MRT_PURPLE || it.type == UiType.MRT_YELLOW
                    }
            }
            TypeSelected.APL -> {
                _uiStations.value =
                    _uiStationsData.value?.filter { it.type == UiType.APL }
            }
            TypeSelected.SRT -> {
                _uiStations.value =
                    _uiStationsData.value?.filter {
                        it.type == UiType.RED_NORMAL || it.type == UiType.RED_WEAK
                    }
            }
        }
    }
}