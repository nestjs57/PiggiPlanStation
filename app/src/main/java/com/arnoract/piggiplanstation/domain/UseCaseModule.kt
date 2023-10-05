package com.arnoract.piggiplanstation.domain

import com.arnoract.piggiplanstation.domain.main.FindShortestPathUseCase
import com.arnoract.piggiplanstation.domain.main.GetStationsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetStationsUseCase(get()) }
    factory { FindShortestPathUseCase(get()) }
}