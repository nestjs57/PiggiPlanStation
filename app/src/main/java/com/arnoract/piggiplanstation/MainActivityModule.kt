package com.arnoract.piggiplanstation

import com.arnoract.piggiplanstation.ui.main.detail.DestinationDetailViewModel
import com.arnoract.piggiplanstation.ui.main.detail.SelectStationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel {
        MainActivityViewModel(get(), get(), get())
    }
    viewModel { (stationEndId: String) ->
        DestinationDetailViewModel(stationEndId, get(), get(), get())
    }
    viewModel {
        SelectStationViewModel(get(), get())
    }
}