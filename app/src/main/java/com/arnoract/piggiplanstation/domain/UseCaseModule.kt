package com.arnoract.piggiplanstation.domain

import com.arnoract.piggiplanstation.domain.main.GetStationNearByUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetStationNearByUseCase(get()) }
}