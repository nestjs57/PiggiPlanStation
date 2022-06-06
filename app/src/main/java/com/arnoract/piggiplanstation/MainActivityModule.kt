package com.arnoract.piggiplanstation

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel {
        MainActivityViewModel(get(), get())
    }
}