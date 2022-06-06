package com.arnoract.piggiplanstation.di

import com.arnoract.piggiplanstation.core.sharedPreferencesModule
import org.koin.core.context.loadKoinModules

object AppComponent {
    fun init() = loadKoinModules(
        listOf(
            coreModule,
            apiModule,
            databaseModule,
            apiModule,
            sharedPreferencesModule
        )
    )
}