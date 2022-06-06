package com.arnoract.piggiplanstation.domain

import com.arnoract.piggiplanstation.domain.main.StationRepository
import com.arnoract.piggiplanstation.domain.main.StationRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single<StationRepository> { StationRepositoryImpl(androidContext()) }
}